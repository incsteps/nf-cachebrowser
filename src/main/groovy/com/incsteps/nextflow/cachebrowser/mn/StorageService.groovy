package com.incsteps.nextflow.cachebrowser.mn

import com.incsteps.nextflow.cachebrowser.mn.model.Execution
import com.incsteps.nextflow.cachebrowser.mn.model.TaskDetail

interface StorageService {

    List<Execution> executions()

    List<TaskDetail> tasks(String session)
}