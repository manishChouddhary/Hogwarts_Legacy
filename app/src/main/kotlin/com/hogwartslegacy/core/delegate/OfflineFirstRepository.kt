package com.hogwartslegacy.core.delegate

import com.hogwartslegacy.core.HogwartsRepository
import com.hogwartslegacy.core.data.local.LocalDataSource
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.remote.RemoteDataSource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class OfflineFirstRepository(
    private val localDataStore: LocalDataSource,
    private val remoteDataStore: RemoteDataSource,
    private val syncContext: CoroutineContext = NonCancellable
) : HogwartsRepository {
    private val syncMutex = Mutex()
    private var refreshRequired = true
    override fun getCharacters(): Flow<List<HogwartsCharacter>> = flow {
        if (!localDataStore.isEmpty()) {
            emit(localDataStore.getAllCharacters().first())
        }
        syncRemoteData()
        emitAll(localDataStore.getAllCharacters())
    }

    override fun getCharacter(id: String): Flow<HogwartsCharacter> = localDataStore.getCharacter(id)

    private suspend fun syncRemoteData() {
        withContext(syncContext) {
            syncMutex.withLock {
                val empty = localDataStore.isEmpty()
                if (empty || refreshRequired) {
                    val remoteCharacters = remoteDataStore.getCharacters()
                    if (remoteCharacters.isEmpty())
                        refreshRequired = true
                    else {
                        localDataStore.updateAllCharacter(remoteCharacters)
                        refreshRequired = false
                    }
                }
            }
        }
    }
}