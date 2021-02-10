package com.rheagroup.efcr.app.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rheagroup.efcr.app.R

class DisplayMessageFragment : Fragment() {
    private val args: DisplayMessageFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.display_message, container, false)

        view.findViewById<TextView>(R.id.textView).apply {
            text = args.message
        }

        return view
    }
}
