package com.incsteps.nextflow.cachebrowser.mn.storages


import com.incsteps.nextflow.cachebrowser.mn.StorageService
import com.incsteps.nextflow.cachebrowser.mn.model.Execution
import com.incsteps.nextflow.cachebrowser.mn.model.History
import com.incsteps.nextflow.cachebrowser.mn.model.TaskDetail
import groovy.util.logging.Slf4j

import java.nio.file.Paths

@Slf4j
abstract class AbstractStorage implements StorageService{

    private final String nextflowDir

    AbstractStorage(String nextflowDir) {
        this.nextflowDir = nextflowDir
    }

    final List<Execution> executions(){
        executionsImpl()
    }

    final List<TaskDetail> tasks(String session){
        tasksImpl(session)
    }

    final void renameWorkDir(String oldPath, String newPath, String task){
        renameWorkDir0(oldPath, newPath, task)
    }

    private void renameWorkDir0(String oldPath, String newPath, String task){
        newPath = newPath.endsWith("/") ? newPath : "$newPath/"
        def executions = executions()
        executions.each {execution->
            def session = execution.session
            def details = tasks(session)
            details.each{ detail ->
                if( !detail.trace.containsKey('workdir') ){
                    return
                }
                if( !detail.trace.containsKey('hash') ){
                    return
                }
                if( !detail.trace['workdir'].toString().startsWith(oldPath) ){
                    return
                }
                if( task && detail.name != task){
                    return
                }
                String current = detail.trace['workdir']
                String hash = detail.trace['hash']
                String base = newPath
                base += current.substring(current.indexOf(hash))
                detail.trace['workdir'] = base
            }
            saveTasks(session, details)
        }
    }

    protected abstract List<Execution> executionsImpl()

    protected abstract List<TaskDetail> tasksImpl(String session)

    protected abstract void saveTasks(String session, List<TaskDetail>tasks)

    protected String getNextflowDir(){
        nextflowDir
    }

    protected List<History>historyList(){
        def history = Paths.get(nextflowDir, ".nextflow", "history").toFile()
        if( !history.exists() && !history.isFile()) {
            throw new IllegalArgumentException("Missing .nextflow/history file")
        }

        def lines = history.text.lines()

        lines.filter(l->l.split("\t").size()==7).collect { String line ->
            History.fromLine(line)
        } as List<History>
    }

}
