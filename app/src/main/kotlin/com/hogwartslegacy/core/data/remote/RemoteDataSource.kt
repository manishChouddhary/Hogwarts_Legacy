package com.hogwartslegacy.core.data.remote

import com.hogwartslegacy.core.data.model.HogwartsCharacter

interface RemoteDataSource {
    /**
     * Get all character list from remote data source
     * throws [FatalException] if failed to fetch data]
     */
    suspend fun getCharacters(): List<HogwartsCharacter>
}