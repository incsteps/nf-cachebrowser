package com.incsteps.nextflow.cachebrowser.mn

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.type.Argument
import io.micronaut.serde.Decoder
import io.micronaut.serde.Encoder
import io.micronaut.serde.Serde
import jakarta.inject.Singleton
import nextflow.processor.TaskId


@CompileStatic
@Singleton
class TaskIdSerde implements Serde<TaskId> {


    @Override
    TaskId deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super TaskId> type) throws IOException {
        Integer number = decoder.decodeInt()
        return new TaskId(number)
    }

    @Override
    void serialize(@NonNull Encoder encoder, @NonNull EncoderContext context, @NonNull Argument<? extends TaskId> type, @NonNull TaskId value) throws IOException {
        encoder.encodeInt(value.intValue())
    }
}