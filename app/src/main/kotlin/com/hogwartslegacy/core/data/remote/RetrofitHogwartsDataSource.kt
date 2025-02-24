package com.hogwartslegacy.core.data.remote

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import java.io.IOException

class RetrofitHogwartsDataSource(private val service: HogwartsService): RemoteDataSource {
    override suspend fun getCharacters(): List<HogwartsCharacter> {
        try {
            val response = service.getCharacters()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw FatalException(FatalException.Type.EMPTY_RESPONSE_BODY)
            } else throw FatalException(FatalException.Type.UNSUCCESSFUL_RESPONSE)
        } catch (ex: IOException) {
            throw FatalException(FatalException.Type.NETWORK_ERROR)
        }
    }
}