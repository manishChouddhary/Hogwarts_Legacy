package com.hogwartslegacy.core.local

import com.hogwartslegacy.core.data.local.HogwartsCharacterSerializer
import com.hogwartslegacy.core.data.local.HogwartsLocalDataStore
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.model.Wand
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rule.ProtoDataStoreRule

class HogwartsLocalDataStoreTest {

    private val testScope = TestScope()

    @get:Rule
    val dataStoreRule = ProtoDataStoreRule(testScope, HogwartsCharacterSerializer())

    private lateinit var dataStore: HogwartsLocalDataStore

    @Before
    fun setUp() {
        dataStore = HogwartsLocalDataStore(dataStoreRule.dataStore)
    }

    @Test
    fun `Given datastore is empty when access then isEmpty returns true`() {
        testScope.runTest {
            assertTrue(dataStore.isEmpty())
        }
    }

    @Test
    fun `Given datastore when updated with characterList then data store is updated`() {
        testScope.runTest {
            dataStore.updateAllCharacter(listOf(character))
            assertEquals(character, dataStore.getAllCharacters().first().first())
        }
    }

    @Test
    fun `Given character id when data store asked for character then character is returned`() {
        testScope.runTest {
            val otherCharacter = character.copy(id = "otherId")
            dataStore.updateAllCharacter(listOf(character, otherCharacter))
            assertEquals(character, dataStore.getCharacter(character.id).first())
        }
    }

    companion object {
        private val character = HogwartsCharacter(
            actor = "actor",
            alive = true,
            ancestry = "ancestry",
            dateOfBirth = "dateOfBirth",
            eyeColour = "eyeColour",
            gender = "gender",
            hairColour = "hairColour",
            hogwartsStaff = true,
            hogwartsStudent = true,
            house = HogwartsCharacter.House.GRYFFINDOR,
            id = "id",
            image = "image",
            name = "name",
            species = "species",
            wand = Wand("core", 1f, "wood"),
            wizard = true,
            yearOfBirth = 1
        )
    }
}