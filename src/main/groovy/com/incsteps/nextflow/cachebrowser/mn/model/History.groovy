package com.incsteps.nextflow.cachebrowser.mn.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class History {
    String date
    String duration
    String name
    String status
    String id
    String session
    String cmd

    static History fromLine(String line) {
        String[] fields = line.split("\t") as String[]
        new History(
                date: fields[0],
                duration: fields[1],
                name: fields[2],
                status: fields[3],
                id: fields[4],
                session: fields[5],
                cmd: fields[6],
        )
    }
}
