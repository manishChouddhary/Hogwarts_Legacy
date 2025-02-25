package com.hogwartslegacy.core.data.model

import com.google.gson.annotations.SerializedName

data class HogwartsCharacter(
    val actor: String,
    val alive: Boolean,
    val ancestry: String,
    val dateOfBirth: String?,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: House?,
    val id: String,
    val image: String?,
    val name: String,
    val species: String,
    val wand: Wand,
    val wizard: Boolean,
    val yearOfBirth: Int?
) {
    enum class House {
        @SerializedName("Gryffindor")
        GRYFFINDOR,
        @SerializedName("Slytherin")
        SLYTHERIN,
        @SerializedName("Ravenclaw")
        RAVENCLAW,
        @SerializedName("Hufflepuff")
        HUFFLEPUFF
    }
}

data class Wand(
    val core: String,
    val length: Float?,
    val wood: String
)