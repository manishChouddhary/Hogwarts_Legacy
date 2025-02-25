package com.hogwartslegacy.characterlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.hogwartslegacy.R
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.characterlist.presentation.HogwartsCharacterState
import com.hogwartslegacy.ui.theme.HogwartsLegacyTheme
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme


@Composable
internal fun CharacterContent(
    character: HogwartsCharacterState,
    modifier: Modifier = Modifier
) {
    val extendedColors = LocalExtendedColorScheme.current
    Box(
        modifier = Modifier
            .background(extendedColors.surface, RoundedCornerShape(10.dp))
            .shadow(elevation = 2.dp, RoundedCornerShape(10.dp), clip = true)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            character.profile?.let {
                AsyncImage(
                    error = painterResource(R.drawable.profile),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(54.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.name,
                        fontSize = 26.sp,
                        color = extendedColors.primaryText
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .background(
                                color = character.houseColor(),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .size(20.dp),
                    )
                }
                Text(
                    text = character.species,
                    fontSize = 20.sp,
                    color = extendedColors.secondaryText
                )
                Text(
                    text = stringResource(R.string.played_by_actor, character.actorName),
                    fontSize = 20.sp,
                    color = extendedColors.secondaryText
                )
            }
        }
    }
}

@Composable
private fun HogwartsCharacterState.houseColor(): Color {
    val extendedColors = LocalExtendedColorScheme.current
    return when (this.house) {
        HogwartsCharacter.House.GRYFFINDOR -> extendedColors.gryffindor
        HogwartsCharacter.House.SLYTHERIN -> extendedColors.slytherin
        HogwartsCharacter.House.RAVENCLAW -> extendedColors.ravenclaw
        HogwartsCharacter.House.HUFFLEPUFF -> extendedColors.hufflepuff
        null -> Color.Transparent
    }
}

@Composable
@Preview
private fun CharacterContentPreview() {
    HogwartsLegacyTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            CharacterContent(
                HogwartsCharacterState(
                    id = "id",
                    name = "Harry Potter",
                    alive = true,
                    house = HogwartsCharacter.House.GRYFFINDOR,
                    profile = null,
                    isStudent = true,
                    actorName = "Daniel",
                    species = "human"
                )
            )
        }
    }
}

@Composable
@Preview
private fun CharacterContentDarkPreview() {
    HogwartsLegacyTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CharacterContent(
                HogwartsCharacterState(
                    id = "id",
                    name = "Harry Potter",
                    alive = false,
                    house = HogwartsCharacter.House.HUFFLEPUFF,
                    profile = "",
                    isStudent = true,
                    actorName = "Daniel",
                    species = "human"
                )
            )
        }
    }
}