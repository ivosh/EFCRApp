package com.rheagroup.efcr.login.data

data class LoggedInState(val state: Kind, val data: LoggedInUser?) {
    enum class Kind { LOGGED_IN, LOGGED_OUT }

    companion object {
        fun loggedIn(data: LoggedInUser) = LoggedInState(Kind.LOGGED_IN, data )
        fun loggedOut() = LoggedInState(Kind.LOGGED_OUT, null)
    }
}
