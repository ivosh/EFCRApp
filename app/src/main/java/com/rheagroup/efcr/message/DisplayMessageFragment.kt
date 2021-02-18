package com.rheagroup.efcr.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rheagroup.efcr.databinding.DisplayMessageBinding

class DisplayMessageFragment : Fragment() {
    private val args: DisplayMessageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DisplayMessageBinding.inflate(inflater, container, false)

        binding.textView.text = args.message

        return binding.root
    }
}
