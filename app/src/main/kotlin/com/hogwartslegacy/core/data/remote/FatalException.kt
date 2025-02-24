package com.hogwartslegacy.core.data.remote

class FatalException(type: Type = Type.GENERIC) : Exception(type.message) {
    enum class Type(val message: String) {
        NETWORK_ERROR("Network error: Unable to get character list"),
        UNSUCCESSFUL_RESPONSE("Response error: Unsuccessful request"),
        GENERIC("Unknown error: unable to process"),
        EMPTY_RESPONSE_BODY("Empty response: No list found")
    }
}