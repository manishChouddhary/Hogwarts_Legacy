package com.hogwartslegacy.core.data.remote

import com.hogwartslegacy.core.data.model.HogwartsCharacter

interface RemoteDataSource {
    suspend fun getCharacters(): List<HogwartsCharacter>
}