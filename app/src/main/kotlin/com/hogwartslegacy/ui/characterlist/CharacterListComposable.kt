package com.hogwartslegacy.ui.characterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hogwartslegacy.presentation.CharacterListViewModel
import com.hogwartslegacy.presentation.HogwartsCharacterState
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListComposable(onCharacterSelected: (String) -> Unit) {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val extendedColors = LocalExtendedColorScheme.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(extendedColors.surface),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.isLoading -> LoadingContent()
            state.isError -> ErrorContent()
            else -> state.characterList?.let { CharacterListContent(it, onCharacterSelected) }
        }
    }
}

@Composable
fun LoadingContent() {

}

@Composable
fun ErrorContent() {

}

@Composable
fun CharacterListContent(
    characterList: List<HogwartsCharacterState>,
    onCharacterSelected: (String) -> Unit
) {
    val extendedColors = LocalExtendedColorScheme.current
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.background(extendedColors.background)
    ) {
        items(characterList) { character ->
            CharacterContent(character, modifier = Modifier.clickable {
                onCharacterSelected(character.id)
            })
        }
    }
}
