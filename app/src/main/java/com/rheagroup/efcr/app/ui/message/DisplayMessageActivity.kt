package com.rheagroup.efcr.app.ui.message

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.rheagroup.efcr.app.R

const val EXTRA_MESSAGE = "com.rheagroup.efcr.app.MESSAGE"

class DisplayMessageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }
}