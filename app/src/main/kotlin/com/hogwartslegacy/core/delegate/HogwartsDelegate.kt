package com.hogwartslegacy.core.delegate

import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.HogwartsRepository
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Hogwarts delegate implementation of [HogwartsCore]
 * access repository methods
 */
class HogwartsDelegate(private val repository: HogwartsRepository) : HogwartsCore {
    override fun getCharacters(): Flow<List<HogwartsCharacter>> = repository.getCharacters()
    override fun getCharacter(id: String): Flow<HogwartsCharacter> = repository.getCharacter(id)
}