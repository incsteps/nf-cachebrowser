package com.incsteps.nextflow.cachebrowser.mn.storages


import com.incsteps.nextflow.cachebrowser.mn.StorageService
import com.incsteps.nextflow.cachebrowser.mn.model.History

import java.nio.file.Paths

abstract class AbstractStorage implements StorageService{

    private final String nextflowDir

    AbstractStorage(String nextflowDir) {
        this.nextflowDir = nextflowDir
    }

    protected String getNextflowDir(){
        nextflowDir
    }

    List<History>historyList(){
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
