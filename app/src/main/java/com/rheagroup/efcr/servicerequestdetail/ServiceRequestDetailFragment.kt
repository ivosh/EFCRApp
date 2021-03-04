package com.rheagroup.efcr.servicerequestdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.rheagroup.efcr.R
import com.rheagroup.efcr.databinding.ServiceRequestDetailBinding
import com.rheagroup.efcr.util.prettyPrintDetailed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceRequestDetailFragment : Fragment() {
    private lateinit var binding: ServiceRequestDetailBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private val args: ServiceRequestDetailFragmentArgs by navArgs()
    private val viewModel: ServiceRequestDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ServiceRequestDetailBinding.inflate(inflater, container, false)

        if (checkBiometricAuthentication(requireContext())) {
            binding.signButton.isEnabled = true
            binding.signButton.setOnClickListener {
                biometricPrompt.authenticate(promptInfo())
            }
        } else {
            binding.signButton.isEnabled = false
        }

        val executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor, biometricAuthentication)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getServiceRequest(args.serviceRequestId).observe(viewLifecycleOwner) {
            binding.serviceRequestName.text = it.name
            binding.serviceRequestStatus.text = it.status
            binding.serviceRequestDate.text = prettyPrintDetailed(it.date)
        }
    }

    private fun promptInfo(): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(resources.getString(R.string.biometric_prompt_title))
            setSubtitle(resources.getString(R.string.biometric_prompt_subtitle))
            setDescription(resources.getString(R.string.biometric_prompt_description))
            setNegativeButtonText(resources.getString(R.string.biometric_prompt_negative))
            setDeviceCredentialAllowed(false)
            // :TODO: On Android 11 (SDK 30) we should use the following method:
            // setAllowedAuthenticators(BIOMETRIC_STRONG)
        }.build()

    private fun checkBiometricAuthentication(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("Biometric authentication", "Can authenticate using biometrics.")
                return true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("Biometric authentication", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("Biometric authentication", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                // :TODO: On Android 11 (SDK 30) we should use ACTION_BIOMETRIC_ENROLL with an extra.
                val enrollIntent = Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                startActivity(enrollIntent)
            }
        }
        return false
    }

    private val biometricAuthentication = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Snackbar.make(binding.root, "Authentication error: $errString", Snackbar.LENGTH_LONG)
                .show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Snackbar.make(binding.root, "Authentication succeeded!", Snackbar.LENGTH_LONG).show()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Snackbar.make(binding.root, "Authentication failed", Snackbar.LENGTH_LONG).show()
        }
    }
}
