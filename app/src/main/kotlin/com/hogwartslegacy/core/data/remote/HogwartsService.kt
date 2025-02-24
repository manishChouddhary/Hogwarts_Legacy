package com.hogwartslegacy.core.data.remote

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import retrofit2.Response
import retrofit2.http.GET

interface HogwartsService{
    @GET("api/characters")
    suspend fun getCharacters(): Response<List<HogwartsCharacter>>
}