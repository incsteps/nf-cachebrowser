package com.incsteps.nextflow.cachebrowser.mn.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class TaskDetail {
    Long id
    int refCount
    Map<String, Object> trace
    Map<String, Object> context

    @Override
    String toString() {
        trace?.name ?: "$id"
    }

    String getName() {
        trace?.name ?: "$id"
    }

    String getStatus(){
        trace?.status
    }

    boolean isFailed(){
        trace?.status != 'COMPLETED'
    }
}
