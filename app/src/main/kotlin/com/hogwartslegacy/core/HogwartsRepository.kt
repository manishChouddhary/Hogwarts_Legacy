package com.hogwartslegacy.core

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

interface HogwartsRepository {
    fun getCharacters(): Flow<List<HogwartsCharacter>>
    fun getCharacter(id: String): Flow<HogwartsCharacter>
}