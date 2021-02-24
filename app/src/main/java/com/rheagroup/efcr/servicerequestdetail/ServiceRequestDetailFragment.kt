package com.rheagroup.efcr.servicerequestdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rheagroup.efcr.databinding.ServiceRequestDetailBinding
import com.rheagroup.efcr.util.prettyPrintDetailed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceRequestDetailFragment : Fragment() {
    private lateinit var binding: ServiceRequestDetailBinding
    private val args: ServiceRequestDetailFragmentArgs by navArgs()
    private val viewModel: ServiceRequestDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ServiceRequestDetailBinding.inflate(inflater, container, false)
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
}
