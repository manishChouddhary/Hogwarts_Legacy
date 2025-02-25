package com.hogwartslegacy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.extension.dateShortMonthYear
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal data class CharacterDetailState(
    val character: CharacterState? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

internal data class CharacterState(
    val name: String,
    val actor: String,
    val alive: Boolean,
    val house: HogwartsCharacter.House?,
    val profile: String?,
    val isStaff: Boolean,
    val dateOfBirth: String?,
    val species: String,
    val gender: String
)

class CharacterDetailViewModel(
    characterId: String,
    hogwartsCore: HogwartsCore
) : ViewModel() {
    internal val state = hogwartsCore.getCharacter(characterId).map {
        CharacterDetailState(
            character = CharacterState(
                name = it.name,
                actor = it.actor,
                alive = it.alive,
                house = it.house,
                profile = it.image,
                isStaff = it.hogwartsStaff,
                dateOfBirth = it.dateOfBirth?.dateShortMonthYear(),
                species = it.species,
                gender = it.gender
            )
        )
    }.catch {
        Log.d("List:", "Error loading list " + it.message)
        emit(CharacterDetailState(isError = true))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(BACKGROUND_THRESHOLD),
        CharacterDetailState(isLoading = true)
    )
}