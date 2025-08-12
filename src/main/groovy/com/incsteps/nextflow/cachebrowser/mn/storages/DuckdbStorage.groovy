package com.incsteps.nextflow.cachebrowser.mn.storages

import com.esotericsoftware.kryo.io.Input
import com.incsteps.nextflow.cachebrowser.mn.model.Execution
import com.incsteps.nextflow.cachebrowser.mn.model.TaskDetail
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import nextflow.exception.AbortOperationException
import nextflow.util.KryoHelper
import org.duckdb.DuckDBResultSet


@Slf4j
@Singleton
@Requires(property = "storage", value = "duckdb")
class DuckdbStorage extends AbstractStorage{

    private final String jdbc
    private final String nextflowDir
    private Sql sql

    DuckdbStorage(
            @Value('${storage.dir}') String nextflowDir,
            @Value('${duckdb.jdbc}')String jdbc) {
        super(nextflowDir)
        Class.forName("org.duckdb.DuckDBDriver")
        this.jdbc = "jdbc:duckdb:$jdbc"
        this.nextflowDir = nextflowDir
    }

    @Override
    protected List<Execution> executionsImpl() {
        historyList().each{ history->

            List<Map> index = loadIndex(history.session, history.name)
            List<Long> taskIds = index.collect{ it.key as Long}

            new Execution(
                    date: history.date,
                    duration: history.duration,
                    id: history.id,
                    name: history.name,
                    status: history.status,
                    session: history.session,
                    cmd: history.cmd,
                    tasks: taskIds
            )
        } as List<Execution>
    }

    @Override
    List<TaskDetail> tasksImpl(String session) {
        def tasks = loadTasks(session)
        return tasks
    }

    protected void validateConnection(){
        try{
            sql = Sql.newInstance(jdbc,'org.duckdb.DuckDBDriver')
        }catch(Exception e){
            log.error "Error validating cache connection",e
            throw new AbortOperationException("Invalid connection for nf-duckdbcache: $e.message")
        }
    }

    protected List<Map>loadIndex(String session, String name){
        validateConnection()
        def rows = sql.rows("select * from INDEX_FILE where session_id=:session_id and name=:name",[
                session_id:session, name:name
        ])
        def list = rows.inject([],{ list, row->
            list << [key:row.id, cached:row.cached]
        }) as List<Map>
        list
    }

    protected List<TaskDetail>loadTasks(String session){
        validateConnection()
        def rows = sql.rows("select * from CACHE_ENTRIES where session_id=:session_id",[
                session_id:session
        ])
        def tasks = [] as List<TaskDetail>
        rows.each{ row->
            def kryo = KryoHelper.kryo()
            DuckDBResultSet.DuckDBBlobResult entry = row?.entry as DuckDBResultSet.DuckDBBlobResult
            byte[] bytes = entry?.binaryStream?.readAllBytes()
            def list = kryo.readClassAndObject(new Input(bytes)) as List<byte[]>
            def trace = kryo.readClassAndObject(new Input(list[0])) as Map<String,Object>
            Map<String,Object> ctx = [:] as Map<String,Object>
            if (list[1]?.length) {
                ctx = kryo.readClassAndObject(new Input(list[1])) as Map<String,Object>
            }
            def refCount = list[2] as Integer
            tasks.add new TaskDetail(id: row.id as long, trace: trace, context: ctx, refCount: refCount)
        }
        return tasks.sort{ it.trace?.start}.reverse()
    }

    @Override
    protected void saveTasks(String session, List<TaskDetail> tasks) {

    }
}
