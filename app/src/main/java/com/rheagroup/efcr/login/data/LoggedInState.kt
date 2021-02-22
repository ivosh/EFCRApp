package com.rheagroup.efcr.login.data

sealed class LoggedInState {
    data class LoggedIn(val data: LoggedInUser) : LoggedInState()
    data class LoggedOut(val data: LoggedInUser? = null) : LoggedInState()
}
