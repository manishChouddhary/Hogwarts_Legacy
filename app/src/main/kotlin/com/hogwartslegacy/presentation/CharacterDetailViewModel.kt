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

internal data class CharacterDetailState(
    val character: CharacterState? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

internal data class CharacterState(
    val id: String,
    val name: String,
    val alive: Boolean,
    val house: HogwartsCharacter.House?,
    val profile: String?,
    val isStaff: Boolean,
)

class CharacterDetailViewModel(
    characterId: String,
    hogwartsCore: HogwartsCore
) : ViewModel() {
    internal val state = hogwartsCore.getCharacter(characterId).map {
        CharacterDetailState(
            character = CharacterState(
                id = it.id,
                name = it.name,
                alive = it.alive,
                house = it.house,
                profile = it.image,
                isStaff = it.hogwartsStaff
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