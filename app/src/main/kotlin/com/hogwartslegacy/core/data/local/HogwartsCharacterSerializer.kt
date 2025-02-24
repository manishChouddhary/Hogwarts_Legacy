package com.hogwartslegacy.core.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.hogwartslegacy.core.data.local.model.AllHogwartsCharacters
import java.io.InputStream
import java.io.OutputStream

class HogwartsCharacterSerializer : Serializer<AllHogwartsCharacters> {
    override val defaultValue: AllHogwartsCharacters
        get() = AllHogwartsCharacters.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AllHogwartsCharacters =
        try {
            AllHogwartsCharacters.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        }


    override suspend fun writeTo(t: AllHogwartsCharacters, output: OutputStream) =
        t.writeTo(output)
}