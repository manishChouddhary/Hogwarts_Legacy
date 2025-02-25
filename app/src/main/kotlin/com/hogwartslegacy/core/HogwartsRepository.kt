package com.hogwartslegacy.core

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Hogwarts repository interface
 */
interface HogwartsRepository {
    /**
     * Get all character list from repository
     * returns flow of list of [HogwartsCharacter]
     * throws [FatalException] if failed to fetch data]
     */
    fun getCharacters(): Flow<List<HogwartsCharacter>>

    /**
     * Get character by id from repository
     * returns flow of [HogwartsCharacter]
     * @param id character id
     */
    fun getCharacter(id: String): Flow<HogwartsCharacter>
}