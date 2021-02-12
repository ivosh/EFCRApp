package com.rheagroup.efcr.app.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rheagroup.efcr.app.R
import com.rheagroup.efcr.app.databinding.DisplayMessageBinding

class DisplayMessageFragment : Fragment() {
    private val args: DisplayMessageFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DisplayMessageBinding.inflate(inflater, container, false)

        binding.textView.text = args.message

        return binding.root
    }
}
