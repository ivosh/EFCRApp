package com.rheagroup.efcr.app.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class TokenProvider @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("tokens", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun storeToken(token: String) {
        sharedPreferences.edit { putString(KEY_NAME, token) }
    }

    companion object {
        const val KEY_NAME = "token"
    }
}
