package com.rheagroup.efcr.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.rheagroup.efcr.app.network.ResourceStatus
import com.rheagroup.efcr.databinding.LoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: LoginBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginFormState.observe(viewLifecycleOwner) { loginFormState ->
            binding.loginSignIn.isEnabled = loginFormState.isDataValid
            loginFormState.usernameError?.let {
                binding.loginEmail.error = getString(it)
            }
            loginFormState.passwordError?.let {
                binding.loginPassword.error = getString(it)
            }
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it.kind == ResourceStatus.Kind.LOADING) {
                binding.loginProgressBar.visibility = View.VISIBLE
            }
            if (it.isFinal()) {
                binding.loginProgressBar.visibility = View.INVISIBLE
            }
            if (it.isError()) {
                Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_LONG).show()
            }
        }

        binding.loginEmail.addTextChangedListener(afterTextChangedListener)
        binding.loginPassword.addTextChangedListener(afterTextChangedListener)
        binding.loginPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doLogin()
            }
            false
        }

        binding.loginSignIn.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() = viewModel.login(
        binding.loginEmail.text.toString(),
        binding.loginPassword.text.toString()
    )

    private val afterTextChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            viewModel.loginDataChanged(
                binding.loginEmail.text.toString(),
                binding.loginPassword.text.toString()
            )
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }
    }
}

