package com.hogwartslegacy.characterlist.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hogwartslegacy.R
import com.hogwartslegacy.characterlist.presentation.CharacterListViewModel
import com.hogwartslegacy.characterlist.presentation.HogwartsCharacterState
import com.hogwartslegacy.ui.common.ErrorContent
import com.hogwartslegacy.ui.common.LoadingContent
import com.hogwartslegacy.ui.common.TopBarScaffold
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListComposable(onCharacterSelected: (String) -> Unit) {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val extendedColors = LocalExtendedColorScheme.current
    var search by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.searchCharacters("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(extendedColors.surface),
    ) {
        Crossfade(search) { searching ->
            when {
                searching -> SearchContent(
                    onBackClick = {
                        search = false
                        viewModel.searchCharacters("")
                    },
                    onSearch = {
                        if (it.length >= 3)
                            viewModel.searchCharacters(it)
                    }
                )

                else -> TopBarScaffold(
                    title = stringResource(R.string.app_name),
                    onSearchClick = {
                        search = true
                    }
                )
            }
        }
        when {
            state.isLoading -> LoadingContent()
            state.isError -> ErrorContent()
            else -> state.characterList?.let { CharacterListContent(it, onCharacterSelected) }
        }
    }
}

@Composable
fun SearchContent(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    val extendedColors = LocalExtendedColorScheme.current
    var searchQuery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .background(extendedColors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_cta)
            )
        }
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearch(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        searchQuery = ""
                        onSearch("")
                    })
            },
            singleLine = true
        )
    }
}

@Composable
private fun CharacterListContent(
    characterList: List<HogwartsCharacterState>,
    onCharacterSelected: (String) -> Unit
) {
    val extendedColors = LocalExtendedColorScheme.current
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
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
