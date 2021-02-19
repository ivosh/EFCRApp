package com.rheagroup.efcr.servicerequestlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rheagroup.efcr.app.network.Status
import com.rheagroup.efcr.databinding.ServiceRequestsListBinding
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class ServiceRequestListFragment : Fragment() {
    private lateinit var binding: ServiceRequestsListBinding
    private val viewModel: ServiceRequestListViewModel by viewModels()

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
            when (it) { // :TODO: Display an error briefly
                Status.SUCCESS, Status.ERROR -> binding.serviceRequestList.isRefreshing = false
            }
        }
    }

    private fun populateServiceRequests(serviceRequests: List<ServiceRequest>) {
        binding.serviceRequestListItems.adapter = ServiceRequestListAdapter(serviceRequests)
    }
}