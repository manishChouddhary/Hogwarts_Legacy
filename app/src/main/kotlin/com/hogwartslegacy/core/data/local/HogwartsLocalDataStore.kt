package com.hogwartslegacy.core.data.local

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

class HogwartsLocalDataStore:LocalDataSource {

    override fun getCharacter(id: String): Flow<HogwartsCharacter> {
        TODO("Not yet implemented")
    }
}