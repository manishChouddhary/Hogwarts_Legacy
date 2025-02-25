package com.hogwartslegacy.characterlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

internal data class CharacterListState(
    val characterList: List<HogwartsCharacterState>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

internal data class HogwartsCharacterState(
    val id: String,
    val name: String,
    val alive: Boolean,
    val house: HogwartsCharacter.House?,
    val profile: String?,
    val isStudent: Boolean,
    val actorName: String,
    val species: String
)

class CharacterListViewModel(hogwartsCore: HogwartsCore) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    internal val state: StateFlow<CharacterListState> =
        combine(
            searchQuery,
            hogwartsCore.getCharacters()
        ) { query, characterList ->
            CharacterListState(
                characterList
                    .filter {
                        it.name.contains(query, ignoreCase = true)
                                || it.actor.contains(query, ignoreCase = true)
                                || query.isEmpty()
                    }
                    .map { character ->
                        HogwartsCharacterState(
                            id = character.id,
                            name = character.name,
                            alive = character.alive,
                            house = character.house,
                            profile = character.image,
                            isStudent = character.hogwartsStudent,
                            actorName = character.actor,
                            species = character.species
                        )
                    }
            )
        }.catch {
            Timber.d(it)
            emit(CharacterListState(isError = true))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            CharacterListState(isLoading = true)
        )

    fun searchCharacters(it: String) {
        searchQuery.value = it
    }
}