package com.incsteps.nextflow.cachebrowser.mn.storages

import com.esotericsoftware.kryo.io.Input
import com.google.common.hash.HashCode
import com.incsteps.nextflow.cachebrowser.mn.model.Execution
import com.incsteps.nextflow.cachebrowser.mn.model.TaskDetail
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import nextflow.cache.CacheStore
import nextflow.util.CacheHelper
import nextflow.util.KryoHelper
import org.iq80.leveldb.DB
import org.iq80.leveldb.Options
import org.iq80.leveldb.impl.Iq80DBFactory

import java.nio.file.Paths

@Slf4j
@Requires(property = "storage", value = "local")
@Singleton
class LocalStorage extends AbstractStorage{

    LocalStorage(@Value('${storage.dir}') String nextflowDir) {
        super(nextflowDir)
    }

    @Override
    protected List<Execution>executionsImpl(){

        def cacheDir = Paths.get(nextflowDir,".nextflow", "cache").toFile()
        if( !cacheDir.exists() && !cacheDir.isDirectory()) {
            throw new IllegalArgumentException("Missing .nextflow/cache subdirectory")
        }

        def ret = historyList().collect{ history->

            List<CacheStore.Index> index = loadIndex(history.session, history.name)
            List<Long> taskIds = index.collect{ it.key.asLong()}

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
        ret
    }

    @Override
    @CompileDynamic
    List<TaskDetail> tasksImpl(String session){
        DB db
        try {
            db = openDB(session)
            if( !db ){
                return []
            }

            def kryo = KryoHelper.kryo()
            def tasks = [] as List<TaskDetail>
            db.each { entry ->

                def hash = HashCode.fromBytes(entry.key)
                def list = kryo.readClassAndObject(new Input(entry.value)) as List<byte[]>
                def trace = kryo.readClassAndObject(new Input(list[0])) as Map<String,Object>
                Map<String,Object> ctx = [:] as Map<String,Object>
                if (list[1]?.length) {
                    ctx = kryo.readClassAndObject(new Input(list[1])) as Map<String,Object>
                }
                def refCount = list[2] as Integer

                tasks.add new TaskDetail(id: hash.asLong(), trace: trace, context: ctx, refCount: refCount)
            }
            return tasks.sort{ it.trace?.start}.reverse()

        }catch (Throwable e){
            e.printStackTrace()
            throw new IllegalArgumentException(e.message)
        }finally {
            if( db != null) {
                db.close()
            }
        }
    }

    protected DB openDB(String session){
        def dbDir = Paths.get(nextflowDir, ".nextflow", "cache", session, "db").toFile()
        if( !dbDir.exists() && !dbDir.isFile()){
            return null
        }
        return Iq80DBFactory.@factory.open(dbDir, new Options().createIfMissing(false))
    }

    protected List<CacheStore.Index>loadIndex(String session, String name){
        def dbDir = Paths.get(nextflowDir, ".nextflow", "cache", session, "index.${name}").toFile()
        if( !dbDir.exists() && !dbDir.isFile()){
            return []
        }
        def ret = [] as List<CacheStore.Index>
        RandomAccessFile indexHandle = new RandomAccessFile(dbDir, "r")
        int KEY_SIZE = CacheHelper.hasher('x').hash().asBytes().size()
        byte[] key = new byte[KEY_SIZE]
        while( indexHandle.read(key) != -1 ){
            def hashCode = HashCode.fromBytes(key)
            final cached = indexHandle.readBoolean()
            ret.add new CacheStore.Index(hashCode, cached)
        }
        return ret
    }

    @Override
    protected void saveTasks(String session, List<TaskDetail> tasks) {
        DB db
        try {
            db = openDB(session)
            if( !db ){
                return
            }
            def kryo = KryoHelper.kryo()
            db.each { entry ->
                def hash = HashCode.fromBytes(entry.key)
                def task = tasks.find{ it.id == hash.asLong()}
                if( !task ){
                    return
                }
                def payload = [
                        KryoHelper.serialize(task.trace),
                        KryoHelper.serialize(task.context),
                        Integer.valueOf(task.refCount)
                ]
                db.put(entry.key, KryoHelper.serialize(payload))
            }

        }catch (Throwable e){
            e.printStackTrace()
            throw new IllegalArgumentException(e.message)
        }finally {
            if( db != null) {
                db.close()
            }
        }
    }
}
