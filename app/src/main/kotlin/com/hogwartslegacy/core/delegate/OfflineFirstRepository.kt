package com.hogwartslegacy.core.delegate

import com.hogwartslegacy.core.HogwartsRepository
import com.hogwartslegacy.core.data.local.LocalDataSource
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.remote.FatalException
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

/**
 * Offline first repository implementation
 * to fetch data from local data source and refresh form remote data source
 */
class OfflineFirstRepository(
    private val localDataStore: LocalDataSource,
    private val remoteDataStore: RemoteDataSource,
    private val syncContext: CoroutineContext = NonCancellable
) : HogwartsRepository {

    /**
     * Mutex ensure only one sync request is active
     */
    private val syncMutex = Mutex()

    /**
     * Flag to indicate if sync is required
     * to ensure we always sync data from remote source when repository is first initialised
     * set to false if remote is failed
     */
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
                    try {
                        val remoteCharacters = remoteDataStore.getCharacters()
                        if (remoteCharacters.isEmpty())
                            refreshRequired = true
                        else {
                            localDataStore.updateAllCharacter(remoteCharacters)
                            refreshRequired = false
                        }
                    } catch (ex: FatalException) {
                        refreshRequired = true
                        if (empty) {
                            throw ex
                        }
                    }
                }
            }
        }
    }
}