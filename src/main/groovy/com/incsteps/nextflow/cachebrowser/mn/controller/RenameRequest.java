package com.incsteps.nextflow.cachebrowser.mn.controller;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RenameRequest(
        String oldPath,
        String newPath,
        String task
) {
}
