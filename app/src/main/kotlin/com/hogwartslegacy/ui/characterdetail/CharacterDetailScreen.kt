package com.hogwartslegacy.ui.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.hogwartslegacy.R
import com.hogwartslegacy.presentation.CharacterDetailViewModel
import com.hogwartslegacy.presentation.CharacterState
import com.hogwartslegacy.ui.common.ErrorContent
import com.hogwartslegacy.ui.common.LoadingContent
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun CharacterDetailScreen(
    characterId: String,
    onBackPress: () -> Unit
) {
    val viewModel =
        koinViewModel<CharacterDetailViewModel>(parameters = { parametersOf(characterId) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    when {
        state.isLoading -> LoadingContent()
        state.isError -> ErrorContent()
        else -> state.character?.let { CharacterDetailContent(it) }
    }
}

@Composable
internal fun CharacterDetailContent(
    character: CharacterState
) {
    val extendedColors = LocalExtendedColorScheme.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(extendedColors.background)
    ) {
        character.profile?.let {
            AsyncImage(
                error = painterResource(R.drawable.profile),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .background(color = extendedColors.surface).fillMaxSize()
            )
        }

    }
}