package com.rheagroup.efcr.servicerequestlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.rheagroup.efcr.databinding.ServiceRequestsListBinding
import com.rheagroup.efcr.login.LoginViewModel
import com.rheagroup.efcr.login.data.LoggedInState
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class ServiceRequestListFragment : Fragment() {
    private lateinit var binding: ServiceRequestsListBinding
    private val viewModel: ServiceRequestListViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = ServiceRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        binding.serviceRequestList.setOnRefreshListener {
            viewModel.fetchServiceRequests()
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {
        viewModel.serviceRequests.observe(viewLifecycleOwner) { serviceRequests ->
            populateServiceRequests(serviceRequests)
        }

        viewModel.fetchStatus.observe(viewLifecycleOwner) {
            if (it.isFinal()) {
                binding.serviceRequestList.isRefreshing = false
            }
            if (it.isError()) {
                Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loggedInState.observe(viewLifecycleOwner) {
            if (it.state == LoggedInState.Kind.LOGGED_OUT) {
                navigateToLogin()
            }
        }
    }

    private fun populateServiceRequests(serviceRequests: List<ServiceRequest>) {
        binding.serviceRequestListItems.adapter = ServiceRequestListAdapter(serviceRequests)
    }

    private fun navigateToLogin() {
        val action = ServiceRequestListFragmentDirections.actionLoggedOut()
        // findNavController().navigate(action) // :TODO: Think better about navigation and what should be accessible logged-out.
    }
}
