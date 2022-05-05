package com.clementcorporation.crysalisreader.model

data class ReaderUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val quote: String,
    val profession: String,
    val avatarUrl: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to userId,
            "display_name" to displayName,
            "quote" to quote,
            "profession" to profession,
            "avatar_url" to avatarUrl
        )
    }
}
