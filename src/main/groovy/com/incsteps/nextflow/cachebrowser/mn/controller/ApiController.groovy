package com.incsteps.nextflow.cachebrowser.mn.controller

import com.incsteps.nextflow.cachebrowser.mn.StorageService
import com.incsteps.nextflow.cachebrowser.mn.model.Execution
import com.incsteps.nextflow.cachebrowser.mn.model.TaskDetail
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api")
class ApiController {

    private final StorageService storageService

    ApiController(StorageService storageService) {
        this.storageService = storageService
    }

    @Get("/executions")
    List<Execution> executions(){
        storageService.executions()
    }

    @Get("/tasks/{session}")
    List<TaskDetail> tasks(String session){
        storageService.tasks(session)
    }
}
