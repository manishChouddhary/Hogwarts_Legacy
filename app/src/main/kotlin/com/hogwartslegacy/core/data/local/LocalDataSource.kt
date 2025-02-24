package com.hogwartslegacy.core.data.local

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllCharacters(): Flow<List<HogwartsCharacter>>
    fun getCharacter(id: String): Flow<HogwartsCharacter>
    suspend fun updateAllCharacter(allCharacter: List<HogwartsCharacter>)
    suspend fun isEmpty(): Boolean
}