package com.rheagroup.efcr.app.ui.servicerequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.rheagroup.efcr.app.databinding.ServiceRequestsListBinding
import com.rheagroup.efcr.app.model.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceRequestListFragment : Fragment() {
    private lateinit var binding: ServiceRequestsListBinding
    private val viewModel: ServiceRequestListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = ServiceRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getServiceRequests().observe(viewLifecycleOwner) { serviceRequests ->
            populateServiceRequests(serviceRequests)
        }
    }

    private fun populateServiceRequests(serviceRequests: List<ServiceRequest>) {
        binding.serviceRequests.adapter = ServiceRequestListAdapter(serviceRequests)
    }
}