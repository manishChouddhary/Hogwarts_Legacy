package com.hogwartslegacy.characterdetail.presentation

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
class CharacterDetailsViewModelTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val characterId = "characterId"
    private val character = mockk<HogwartsCharacter>(relaxed = true) {
        every { name } returns characterState.name
        every { actor } returns characterState.actor
        every { alive } returns characterState.alive
        every { house } returns characterState.house
        every { image } returns characterState.profile
        every { hogwartsStaff } returns characterState.isStaff
        every { dateOfBirth } returns characterState.dateOfBirth
        every { species } returns characterState.species
        every { gender } returns characterState.gender
    }
    private val hogwartsCore = mockk<HogwartsCore> {
        every { getCharacter(characterId) } returns flowOf(character)
    }
    private val viewModel by lazy { CharacterDetailViewModel(characterId, hogwartsCore) }

    @Test
    fun `Given character id When state is loaded initially then loading state is returned`() =
        runTest {
            val outputs = mutableListOf<CharacterDetailState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertTrue(outputs.first().isLoading)
            job.cancel()
        }

    @Test
    fun `Given character id When state is called then state is updated with character returned from core`() =
        runTest {
            val outputs = mutableListOf<CharacterDetailState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertEquals(characterState, outputs.last().character)
            job.cancel()
        }

    @Test
    fun `Given character id When state is called then state is updated with error state`() {
        every { hogwartsCore.getCharacter(characterId) } returns flow { throw FatalException() }
        runTest {
            val outputs = mutableListOf<CharacterDetailState>()
            val job = launch(UnconfinedTestDispatcher()) {
                viewModel.state.toList(outputs)
            }
            assertTrue(outputs.last().isError)
            job.cancel()
        }
    }

    companion object {
        private val characterState = CharacterState(
            name = "name",
            actor = "actor",
            alive = true,
            house = HogwartsCharacter.House.GRYFFINDOR,
            profile = "profile",
            isStaff = true,
            dateOfBirth = "dateOfBirth",
            species = "species",
            gender = "gender"
        )
    }
}