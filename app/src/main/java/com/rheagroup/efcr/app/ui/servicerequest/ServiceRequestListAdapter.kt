package com.rheagroup.efcr.app.ui.servicerequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rheagroup.efcr.app.databinding.ServiceRequestListItemBinding
import com.rheagroup.efcr.app.model.ServiceRequest
import com.rheagroup.efcr.app.util.prettyPrintDateTime

class ServiceRequestListAdapter(private val serviceRequests: List<ServiceRequest>) :
        RecyclerView.Adapter<ServiceRequestListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServiceRequestListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(serviceRequests[position])
    }

    override fun getItemCount() = serviceRequests.size

    inner class ViewHolder(private val binding: ServiceRequestListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(serviceRequest: ServiceRequest) {
            binding.serviceRequestItemName.text = serviceRequest.name
            binding.serviceRequestItemStatus.text = serviceRequest.status
            binding.serviceRequestItemDate.text = prettyPrintDateTime(serviceRequest.date)
        }
    }
}
