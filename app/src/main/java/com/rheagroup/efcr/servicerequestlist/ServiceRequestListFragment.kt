package com.rheagroup.efcr.servicerequestlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rheagroup.efcr.databinding.ServiceRequestListBinding
import com.rheagroup.efcr.login.LoginViewModel
import com.rheagroup.efcr.login.data.LoggedInState
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
class ServiceRequestListFragment : Fragment() {
    private lateinit var binding: ServiceRequestListBinding
    private val adapter by lazy(NONE) { ServiceRequestListAdapter { onItemClicked(it) } }
    private val loginViewModel: LoginViewModel by viewModels()
    private val viewModel: ServiceRequestListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ServiceRequestListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceRequestListItems.adapter = adapter
        subscribeObservers()

        binding.serviceRequestList.setOnRefreshListener {
            viewModel.fetchServiceRequests()
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {
        viewModel.serviceRequests.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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

    private fun onItemClicked(serviceRequest: ServiceRequest) {
        val action = ServiceRequestListFragmentDirections.actionShowServiceRequestDetail(
            serviceRequestId = serviceRequest.id
        )
        findNavController().navigate(action)
    }

    private fun navigateToLogin() {
        val action = ServiceRequestListFragmentDirections.actionLoggedOut()
        // findNavController().navigate(action) // :TODO: Think better about navigation and what should be accessible logged-out.
    }
}
