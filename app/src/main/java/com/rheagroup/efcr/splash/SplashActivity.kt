package com.rheagroup.efcr.splash

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.rheagroup.efcr.MainActivity
import com.rheagroup.efcr.R

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({ redirect()}, 1500L)
    }

    private fun redirect() {
        if (!isFinishing) {
            startActivity(MainActivity(this))
            finish()
        }
    }
}
