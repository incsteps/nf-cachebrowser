package com.incsteps.nextflow.cachebrowser.mn
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class WrapperTaskId extends Number{

    long value
    WrapperTaskId(long value) {
        this.value = value
    }

    @Override
    int intValue() {
        return value as int
    }

    @Override
    long longValue() {
        return value as long
    }

    @Override
    float floatValue() {
        return value as float
    }

    @Override
    double doubleValue() {
        return value as double
    }
}
