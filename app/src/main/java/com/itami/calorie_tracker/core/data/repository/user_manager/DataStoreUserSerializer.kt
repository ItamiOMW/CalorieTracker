package com.itami.calorie_tracker.core.data.repository.user_manager

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object DataStoreUserSerializer: Serializer<DataStoreUser> {

    override val defaultValue: DataStoreUser
        get() = DataStoreUser()

    override suspend fun readFrom(input: InputStream): DataStoreUser {
        return try {
            Json.decodeFromString(
                deserializer = DataStoreUser.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (serializationExc: SerializationException){
            serializationExc.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: DataStoreUser, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = DataStoreUser.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }

}