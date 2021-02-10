package com.rheagroup.efcr.app.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.rheagroup.efcr.app.R

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
