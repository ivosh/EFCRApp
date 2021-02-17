package com.rheagroup.efcr.app.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rheagroup.efcr.app.databinding.SendMessageBinding

class SendMessageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = SendMessageBinding.inflate(inflater, container, false)

        binding.sendButton.setOnClickListener {
            sendMessage(binding.editText.text.toString())
        }

        return binding.root
    }

    private fun sendMessage(message: String) {
        val action = SendMessageFragmentDirections.actionSendMessage(message)
        findNavController().navigate(action)
    }
}
