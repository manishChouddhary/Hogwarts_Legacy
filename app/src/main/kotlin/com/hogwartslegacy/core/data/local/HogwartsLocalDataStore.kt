package com.hogwartslegacy.core.data.local

import androidx.datastore.core.DataStore
import com.hogwartslegacy.core.data.local.model.AllHogwartsCharacters
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class HogwartsLocalDataStore(private val hogwartsDataStore: DataStore<AllHogwartsCharacters>) :
    LocalDataSource {
    override fun getAllCharacters(): Flow<List<HogwartsCharacter>> =
        hogwartsDataStore.data.map {
            it.charactersList.toHogwartsCharacterList()
        }


    override fun getCharacter(id: String): Flow<HogwartsCharacter> =
        hogwartsDataStore.data.map { allCharacter ->
            allCharacter.charactersList.toHogwartsCharacterList().first { id == it.id }
        }

    override suspend fun updateAllCharacter(allCharacter: List<HogwartsCharacter>) {
        hogwartsDataStore.updateData {
            AllHogwartsCharacters.newBuilder()
                .addAllCharacters(allCharacter.toProtoHogwartsCharacterList()).build()
        }
    }

    override suspend fun isEmpty(): Boolean = hogwartsDataStore.data.map {
        it.charactersList.toHogwartsCharacterList()
    }.first().isEmpty()
}