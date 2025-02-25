package com.hogwartslegacy.core

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Hogwarts core functional
 * to clearly indicate what is needed from data layer
 */
interface HogwartsCore {

    /**
     * Get all character list from data layer
     * returns flow of list of [HogwartsCharacter]
     * throws [FatalException] if failed to fetch data]
     */
    fun getCharacters(): Flow<List<HogwartsCharacter>>

    /**
     * Get character by id from data layer
     * returns flow of [HogwartsCharacter]
     * @param id character id
     */
    fun getCharacter(id: String): Flow<HogwartsCharacter>
}