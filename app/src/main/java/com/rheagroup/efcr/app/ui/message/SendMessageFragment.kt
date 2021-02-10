package com.rheagroup.efcr.app.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rheagroup.efcr.app.R

class SendMessageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.send_message, container, false)

        view.findViewById<Button>(R.id.send_button).setOnClickListener {
            sendMessage(view)
        }

        return view
    }

    private fun sendMessage(view: View) {
        val message = view.findViewById<EditText>(R.id.editText).text.toString()

        val action = SendMessageFragmentDirections.actionSendMessage(message)
        view.findNavController().navigate(action)
    }
}
