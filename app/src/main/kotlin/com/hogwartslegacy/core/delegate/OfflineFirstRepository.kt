package com.hogwartslegacy.core.delegate

import com.hogwartslegacy.core.HogwartsRepository
import com.hogwartslegacy.core.data.local.LocalDataSource
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineFirstRepository(
    private val localDataStore: LocalDataSource,
    private val remoteDataStore: RemoteDataSource
) : HogwartsRepository {

    override fun getCharacters(): Flow<List<HogwartsCharacter>> = flow {
        emit(remoteDataStore.getCharacters())
    }

    override fun getCharacter(id: String): Flow<HogwartsCharacter> = localDataStore.getCharacter(id)
}