package com.rheagroup.efcr.login.network

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_expires_in: Int,
    val refresh_token: String
)
