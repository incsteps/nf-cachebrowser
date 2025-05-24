package com.incsteps.nextflow.cachebrowser.mn.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class Execution {

    String date = ""
    String duration = ""
    String id = ""
    String name = ""
    String status = ""
    String session = ""
    String cmd = ""
    List<Long>tasks = []

}
