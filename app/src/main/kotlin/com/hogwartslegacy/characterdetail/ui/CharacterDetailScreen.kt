package com.hogwartslegacy.characterdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import com.hogwartslegacy.R
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.characterdetail.presentation.CharacterDetailViewModel
import com.hogwartslegacy.characterdetail.presentation.CharacterState
import com.hogwartslegacy.ui.common.ErrorContent
import com.hogwartslegacy.ui.common.LoadingContent
import com.hogwartslegacy.ui.common.TopBarScaffold
import com.hogwartslegacy.ui.theme.HogwartsLegacyTheme
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
        else -> state.character?.let { CharacterDetailContent(it, onBackPress) }
    }
}

@Composable
internal fun CharacterDetailContent(
    character: CharacterState,
    onBackPress: () -> Unit
) {
    val extendedColors = LocalExtendedColorScheme.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(extendedColors.background)
    ) {
        TopBarScaffold(character.name, onBackClick = onBackPress)
        Box(
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth()
        ) {
            character.profile?.let {
                AsyncImage(
                    error = painterResource(R.drawable.profile),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .transformations()
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .background(color = extendedColors.surface)
                        .fillMaxSize()
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ValueLabel(
                stringResource(R.string.character),
                character.name,
                deadOrAlive = character.alive
            )
            Divider()
            Row {
                ValueLabel(
                    stringResource(R.string.hogwarts),
                    character.isStaff.studentOfStaff,
                    modifier = Modifier.weight(1f)
                )
                character.house?.let {
                    ValueLabel(
                        stringResource(R.string.from_house),
                        it.houseName(),
                        dotColor = it.houseColor(), modifier = Modifier.weight(1f)
                    )
                }
            }
            Divider()
            Row {
                ValueLabel(
                    stringResource(R.string.species),
                    character.species.uppercase(),
                    modifier = Modifier.weight(1f)
                )
                ValueLabel(
                    stringResource(R.string.gender),
                    character.gender,
                    modifier = Modifier.weight(1f)
                )
            }
            Divider()
            ValueLabel(stringResource(R.string.played_by), character.actor)
            Divider()
            character.dateOfBirth?.let { ValueLabel(stringResource(R.string.date_of_birth), it) }
        }
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalExtendedColorScheme.current.surface.copy(alpha = .5f))
            .height(1.dp)
    )
}

@Composable
private fun ValueLabel(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    deadOrAlive: Boolean? = null,
    dotColor: Color? = null
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            color = LocalExtendedColorScheme.current.primaryText,
            fontSize = 16.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            dotColor?.let {
                Box(
                    Modifier
                        .background(it, shape = RoundedCornerShape(10.dp))
                        .size(10.dp)
                        .padding(end = 8.dp)
                )
            }
            Text(
                text = value,
                color = LocalExtendedColorScheme.current.secondaryText,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge
            )
            deadOrAlive?.let {
                Text(
                    text = it.deadOrAliveText,
                    color = LocalExtendedColorScheme.current.success,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 28.dp)
                )
            }
        }
    }
}

private val Boolean.deadOrAliveText: String
    @Composable
    get() = if (this) stringResource(R.string.alive) else stringResource(R.string.dead)

private val Boolean.studentOfStaff: String
    @Composable
    get() = if (this) stringResource(R.string.staff) else stringResource(R.string.student)

@Composable
private fun HogwartsCharacter.House.houseColor(): Color {
    val extendedColors = LocalExtendedColorScheme.current
    return when (this) {
        HogwartsCharacter.House.GRYFFINDOR -> extendedColors.gryffindor
        HogwartsCharacter.House.SLYTHERIN -> extendedColors.slytherin
        HogwartsCharacter.House.RAVENCLAW -> extendedColors.ravenclaw
        HogwartsCharacter.House.HUFFLEPUFF -> extendedColors.hufflepuff
    }
}

@Composable
private fun HogwartsCharacter.House.houseName(): String = when (this) {
    HogwartsCharacter.House.GRYFFINDOR -> stringResource(R.string.gryffindor)
    HogwartsCharacter.House.SLYTHERIN -> stringResource(R.string.slytherin)
    HogwartsCharacter.House.RAVENCLAW -> stringResource(R.string.ravenclaw)
    HogwartsCharacter.House.HUFFLEPUFF -> stringResource(R.string.hufflepuff)
}

@Preview
@Composable
private fun CharacterDetailsPreview() {
    HogwartsLegacyTheme {
        CharacterDetailContent(
            character = CharacterState(
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                alive = true,
                house = HogwartsCharacter.House.GRYFFINDOR,
                profile = "",
                isStaff = false,
                dateOfBirth = "15-07-1980",
                species = "human",
                gender = "male"
            ),
            onBackPress = { }
        )
    }
}