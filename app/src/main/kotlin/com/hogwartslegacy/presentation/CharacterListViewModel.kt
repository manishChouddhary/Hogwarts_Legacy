package com.hogwartslegacy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

const val BACKGROUND_THRESHOLD = 5000L

data class CharacterListState(
    val characterList: List<HogwartsCharacterState>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class HogwartsCharacterState(
    val id: String,
    val name: String,
    val alive: Boolean,
    val house: HogwartsCharacter.House?,
    val profile: String?,
    val isStudent: Boolean
)

class CharacterListViewModel(hogwartsCore: HogwartsCore) : ViewModel() {
    val state = hogwartsCore.getCharacters().map {
        CharacterListState(
            it.map { character ->
                HogwartsCharacterState(
                    id = character.id,
                    name = character.name,
                    alive = character.alive,
                    house = character.house,
                    profile = character.image,
                    isStudent = character.hogwartsStudent
                )
            }
        )
    }.catch {
        Log.d("List:", "Error loading list "+it.message)
        emit(CharacterListState(isError = true))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(BACKGROUND_THRESHOLD),
        CharacterListState(isLoading = true)
    )
}