package com.hogwartslegacy.core.data.local

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getCharacter(id: String): Flow<HogwartsCharacter>
}