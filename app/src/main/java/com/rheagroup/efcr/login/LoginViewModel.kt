package com.rheagroup.efcr.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.rheagroup.efcr.R
import com.rheagroup.efcr.app.network.ResourceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val triggerLogin = MutableLiveData<TriggerLogin>()
    private val atomicInteger = AtomicInteger()

    @ExperimentalCoroutinesApi
    val loggedInState = repository.getLoggedInState().asLiveData()

    fun login(username: String, password: String) {
        triggerLogin.value = TriggerLogin(username, password, atomicInteger.incrementAndGet())
    }

    val loginStatus = triggerLogin.switchMap {
        liveData {
            emit(ResourceStatus.loading())
            emit(repository.login(it.username, it.password))
        }
    }

    data class TriggerLogin(val username: String, val password: String, private val seq: Int)

    /*-------------------------------------------------------------------------------------------*/

    private val loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = loginForm

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginForm.value = LoginFormState(usernameError = R.string.login_invalid_username)
        } else if (!isPasswordValid(password)) {
            loginForm.value = LoginFormState(passwordError = R.string.login_invalid_password)
        } else {
            loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}
