package com.hogwartslegacy.core.local

import com.hogwartslegacy.core.data.local.model.ProtoHogwartsCharacter
import com.hogwartslegacy.core.data.local.model.ProtoHouse
import com.hogwartslegacy.core.data.local.toHogwartsCharacterList
import com.hogwartslegacy.core.data.local.toHouse
import com.hogwartslegacy.core.data.local.toProtoHogwartsCharacterList
import com.hogwartslegacy.core.data.local.toProtoHouse
import com.hogwartslegacy.core.data.local.toProtoWand
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.model.Wand
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProtoHogwartsMapperTest {
    @Test
    fun `Given hogwarts character when mapped to proto then proto character is filled properly`() {
        val proto = listOf(character).toProtoHogwartsCharacterList().first()
        val builder = ProtoHogwartsCharacter.newBuilder()
            .setActor(character.actor)
            .setAlive(character.alive)
            .setAncestry(character.ancestry)
            .setEyeColour(character.eyeColour)
            .setGender(character.gender)
            .setHairColour(character.hairColour)
            .setHogwartsStaff(character.hogwartsStaff)
            .setHogwartsStudent(character.hogwartsStudent)
            .setId(character.id)
            .setName(character.name)
            .setSpecies(character.species)
            .setWand(character.wand.toProtoWand())
            .setWizard(character.wizard)
        character.house?.toProtoHouse()?.let { house -> builder.setHouse(house) }
        character.dateOfBirth?.let { dateOfBirth -> builder.setDateOfBirth(dateOfBirth) }
        character.image?.let { imageLink -> builder.setImage(imageLink) }
        character.yearOfBirth?.let { yearOfBirth -> builder.setYearOfBirth(yearOfBirth) }
        assertEquals(proto, builder.build())
    }

    @Test
    fun `Given proto character when mapped to hogwarts character then hogwarts character is filled properly`() {
        val builder = ProtoHogwartsCharacter.newBuilder()
            .setActor(character.actor)
            .setAlive(character.alive)
            .setAncestry(character.ancestry)
            .setEyeColour(character.eyeColour)
            .setGender(character.gender)
            .setHairColour(character.hairColour)
            .setHogwartsStaff(character.hogwartsStaff)
            .setHogwartsStudent(character.hogwartsStudent)
            .setId(character.id)
            .setName(character.name)
            .setSpecies(character.species)
            .setWand(character.wand.toProtoWand())
            .setWizard(character.wizard)
        character.house?.toProtoHouse()?.let { house -> builder.setHouse(house) }
        character.dateOfBirth?.let { dateOfBirth -> builder.setDateOfBirth(dateOfBirth) }
        character.image?.let { imageLink -> builder.setImage(imageLink) }
        character.yearOfBirth?.let { yearOfBirth -> builder.setYearOfBirth(yearOfBirth) }

        val hogwarts = listOf(builder.build()).toHogwartsCharacterList().first()
        assertEquals(hogwarts, character)
    }

    @Test
    fun `Given hogwarts house when mapped to proto house then mapped filled properly`() {
        assertEquals(ProtoHouse.GRYFFINDOR, HogwartsCharacter.House.GRYFFINDOR.toProtoHouse())
        assertEquals(ProtoHouse.SLYTHERIN, HogwartsCharacter.House.SLYTHERIN.toProtoHouse())
        assertEquals(ProtoHouse.RAVENCLAW, HogwartsCharacter.House.RAVENCLAW.toProtoHouse())
        assertEquals(ProtoHouse.HUFFLEPUFF, HogwartsCharacter.House.HUFFLEPUFF.toProtoHouse())
    }

    @Test
    fun `Given proto house when mapped to hogwarts house then mapped filled properly`() {
        assertEquals(HogwartsCharacter.House.GRYFFINDOR, ProtoHouse.GRYFFINDOR.toHouse())
        assertEquals(HogwartsCharacter.House.SLYTHERIN, ProtoHouse.SLYTHERIN.toHouse())
        assertEquals(HogwartsCharacter.House.RAVENCLAW, ProtoHouse.RAVENCLAW.toHouse())
        assertEquals(HogwartsCharacter.House.HUFFLEPUFF, ProtoHouse.HUFFLEPUFF.toHouse())
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