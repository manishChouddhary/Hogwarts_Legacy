package com.hogwartslegacy.core.data.local

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Local data source interface
 */
interface LocalDataSource {
    /**
     * Get all character list from local data source
     * returns flow of list of [HogwartsCharacter]
     */
    fun getAllCharacters(): Flow<List<HogwartsCharacter>>

    /**
     * Get character by id from local data source
     * returns flow of [HogwartsCharacter]
     * @param id character id
     */
    fun getCharacter(id: String): Flow<HogwartsCharacter>

    /**
     * Update all character list in local data source
     * @param allCharacter list of [HogwartsCharacter]
     */
    suspend fun updateAllCharacter(allCharacter: List<HogwartsCharacter>)

    /**
     * Check if local data source is empty
     */
    suspend fun isEmpty(): Boolean
}