package com.rheagroup.efcr.login.data

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository.
 */
data class LoggedInUser(
    // val userId: String, // :TODO: decode from token
    // val username: String, // :TODO: decode from token
    val accessToken: String,
    val refreshToken: String
)
