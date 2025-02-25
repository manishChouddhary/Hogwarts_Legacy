package com.hogwartslegacy.characterlist.persentation

import com.hogwartslegacy.characterlist.presentation.CharacterListState
import com.hogwartslegacy.characterlist.presentation.CharacterListViewModel
import com.hogwartslegacy.characterlist.presentation.HogwartsCharacterState
import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.remote.FatalException
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import rule.CoroutineTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val hogwartsCharacter = mockk<HogwartsCharacter> {
        every { id } returns characterState.id
        every { name } returns characterState.name
        every { actor } returns characterState.actorName
        every { alive } returns characterState.alive
        every { house } returns characterState.house
        every { image } returns characterState.profile
        every { hogwartsStudent } returns characterState.isStudent
        every { species } returns characterState.species
    }

    private val hogwartsCore = mockk<HogwartsCore> {
        every { getCharacters() } returns flowOf(listOf(hogwartsCharacter))
    }
    private val viewModel by lazy { CharacterListViewModel(hogwartsCore) }

    @Test
    fun `Given state is called When loaded initially then loading state is returned`() =
        runTest {
            val outputs = mutableListOf<CharacterListState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertTrue(outputs.first().isLoading)
            job.cancel()
        }

    @Test
    fun `Given list of characters available When state is loaded then state is updated with character list`() =
        runTest {
            val outputs = mutableListOf<CharacterListState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertEquals(characterState, outputs.last().characterList?.first())
            job.cancel()
        }

    @Test
    fun `Given list of characters available When state is loaded then state is updated with error state`() {
        every { hogwartsCore.getCharacters() } returns flow { throw FatalException() }
        runTest {
            val outputs = mutableListOf<CharacterListState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertTrue(outputs.last().isError)
            job.cancel()
        }
    }

    @Test
    fun `Given search query When search query is updated then state is updated with search result`() {
        val otherCharacterState = characterState.copy(name = "otherName")
        val otherCharacterActorState = characterState.copy(actorName = "otherActor")

        val otherCharacter = mockk<HogwartsCharacter> {
            every { id } returns otherCharacterState.id
            every { name } returns otherCharacterState.name
            every { actor } returns otherCharacterState.actorName
            every { alive } returns otherCharacterState.alive
            every { house } returns otherCharacterState.house
            every { image } returns otherCharacterState.profile
            every { hogwartsStudent } returns otherCharacterState.isStudent
            every { species } returns otherCharacterState.species
        }
        val otherCharacterActor = mockk<HogwartsCharacter> {
            every { id } returns otherCharacterActorState.id
            every { name } returns otherCharacterActorState.name
            every { actor } returns otherCharacterActorState.actorName
            every { alive } returns otherCharacterActorState.alive
            every { house } returns otherCharacterActorState.house
            every { image } returns otherCharacterActorState.profile
            every { hogwartsStudent } returns otherCharacterActorState.isStudent
            every { species } returns otherCharacterActorState.species
        }

        every { hogwartsCore.getCharacters() } returns flowOf(
            listOf(
                hogwartsCharacter,
                otherCharacter,
                otherCharacterActor
            )
        )
        runTest {
            val outputs = mutableListOf<CharacterListState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            viewModel.searchCharacters("other")
            assertEquals(
                listOf(otherCharacterState, otherCharacterActorState),
                outputs.last().characterList
            )
            job.cancel()
        }
    }

    companion object {
        private val characterState = HogwartsCharacterState(
            id = "id",
            name = "name",
            actorName = "actor",
            alive = true,
            house = HogwartsCharacter.House.GRYFFINDOR,
            profile = "profile",
            isStudent = true,
            species = "species",
        )
    }
}