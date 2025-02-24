package com.hogwartslegacy.core.data.local

import com.hogwartslegacy.core.data.local.model.ProtoHogwartsCharacter
import com.hogwartslegacy.core.data.local.model.ProtoHouse
import com.hogwartslegacy.core.data.local.model.ProtoWand
import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.model.Wand

fun List<ProtoHogwartsCharacter>.toHogwartsCharacterList() = this.map {
    HogwartsCharacter(
        actor = it.actor,
        alive = it.alive,
        ancestry = it.ancestry,
        dateOfBirth = it.dateOfBirth.nullIfEmpty(),
        eyeColour = it.eyeColour,
        gender = it.gender,
        hairColour = it.hairColour,
        hogwartsStaff = it.hogwartsStaff,
        hogwartsStudent = it.hogwartsStudent,
        house = it.house.toHouse(),
        id = it.id,
        image = it.image,
        name = it.name,
        species = it.species,
        wand = it.wand.toWand(),
        wizard = it.wizard,
        yearOfBirth = it.yearOfBirth,
    )
}

fun String.nullIfEmpty(): String? = this.takeIf { it.isNotEmpty() }

fun ProtoHouse.toHouse() = when (this) {
    ProtoHouse.GRYFFINDOR -> HogwartsCharacter.House.GRYFFINDOR
    ProtoHouse.SLYTHERIN -> HogwartsCharacter.House.SLYTHERIN
    ProtoHouse.RAVENCLAW -> HogwartsCharacter.House.RAVENCLAW
    ProtoHouse.HUFFLEPUFF -> HogwartsCharacter.House.HUFFLEPUFF
    ProtoHouse.UNRECOGNIZED -> null
}

fun ProtoWand.toWand() = Wand(
    this.core,
    this.length,
    this.wood
)

fun List<HogwartsCharacter>.toProtoHogwartsCharacterList() = this.map {
    val builder = ProtoHogwartsCharacter.newBuilder()
        .setActor(it.actor)
        .setAlive(it.alive)
        .setAncestry(it.ancestry)
        .setEyeColour(it.eyeColour)
        .setGender(it.gender)
        .setHairColour(it.hairColour)
        .setHogwartsStaff(it.hogwartsStaff)
        .setHogwartsStudent(it.hogwartsStudent)
        .setId(it.id)
        .setName(it.name)
        .setSpecies(it.species)
        .setWand(it.wand.toProtoWand())
        .setWizard(it.wizard)
    it.house?.toProtoHouse()?.let { house -> builder.setHouse(house) }
    it.dateOfBirth?.let { dateOfBirth -> builder.setDateOfBirth(dateOfBirth) }
    it.image?.let { imageLink -> builder.setImage(imageLink) }
    it.yearOfBirth?.let { yearOfBirth -> builder.setYearOfBirth(yearOfBirth) }
    builder.build()
}

fun HogwartsCharacter.House.toProtoHouse() = when (this) {
    HogwartsCharacter.House.GRYFFINDOR -> ProtoHouse.GRYFFINDOR
    HogwartsCharacter.House.SLYTHERIN -> ProtoHouse.SLYTHERIN
    HogwartsCharacter.House.RAVENCLAW -> ProtoHouse.RAVENCLAW
    HogwartsCharacter.House.HUFFLEPUFF -> ProtoHouse.HUFFLEPUFF
}

fun Wand.toProtoWand(): ProtoWand {
    val builder = ProtoWand.newBuilder()
        .setCore(this.core)
        .setWood(this.wood)
    this.length?.let { builder.setLength(it) }
    return builder.build()
}