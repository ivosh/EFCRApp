package com.rheagroup.efcr.app

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

const val EXTRA_MESSAGE = "com.rheagroup.efcr.app.MESSAGE"

class DisplayMessageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }
}