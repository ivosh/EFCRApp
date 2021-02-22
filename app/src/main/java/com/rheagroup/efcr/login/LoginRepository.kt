package com.rheagroup.efcr.login

import com.auth0.android.jwt.JWT
import com.rheagroup.efcr.app.di.IoDispatcher
import com.rheagroup.efcr.app.network.ApiResponse
import com.rheagroup.efcr.app.network.ResourceStatus
import com.rheagroup.efcr.app.network.TokenProvider
import com.rheagroup.efcr.app.network.apiCall
import com.rheagroup.efcr.login.data.LoggedInState
import com.rheagroup.efcr.login.data.LoggedInUser
import com.rheagroup.efcr.login.network.LoginApi
import com.rheagroup.efcr.login.network.LoginResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val remoteApi: LoginApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    // In-memory cache of the logged-in state.
    // :TODO: Think about some persistence (encrypted SharedPreferences?).
    private val loggedInState: MutableStateFlow<LoggedInState> =
        MutableStateFlow(LoggedInState.loggedOut())

    fun getLoggedInState() = loggedInState.asSharedFlow()

    suspend fun login(username: String, password: String): ResourceStatus {
        return withContext(ioDispatcher) {
            val response = apiCall { remoteApi.login(username, password) }
            if (response.kind == ApiResponse.Kind.SUCCESS) {
                setLoggedInUser(extractLoggedInUser(response.data!!))
            }
            response.toResourceStatus()
        }
    }

    suspend fun logout() {
        return withContext(ioDispatcher) {
            loggedInState.emit(LoggedInState.loggedOut())
            tokenProvider.removeToken()
            remoteApi.logout()
        }
    }

    private fun extractLoggedInUser(response: LoginResponse): LoggedInUser {
        JWT(response.access_token) // :TODO: For now check only if JWT can be parsed

        return LoggedInUser(
            accessToken = response.access_token,
            refreshToken = response.refresh_token
        )
    }

    private suspend fun setLoggedInUser(user: LoggedInUser) {
        tokenProvider.storeToken(user.accessToken)
        loggedInState.emit(LoggedInState.loggedIn(user))
    }
}
