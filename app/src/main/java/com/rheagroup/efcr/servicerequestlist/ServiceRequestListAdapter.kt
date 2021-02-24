package com.rheagroup.efcr.servicerequestlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rheagroup.efcr.databinding.ServiceRequestListItemBinding
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import com.rheagroup.efcr.util.prettyPrintElapsed

class ServiceRequestListAdapter(
    private val onItemClicked: (serviceRequest: ServiceRequest) -> Unit
) :
    ListAdapter<ServiceRequest, ServiceRequestListAdapter.ViewHolder>(ListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServiceRequestListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding) {
            onItemClicked(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ListDiff : DiffUtil.ItemCallback<ServiceRequest>() {
        override fun areItemsTheSame(oldItem: ServiceRequest, newItem: ServiceRequest) =
            oldItem.id === newItem.id

        override fun areContentsTheSame(oldItem: ServiceRequest, newItem: ServiceRequest) =
            oldItem == newItem
    }

    inner class ViewHolder(
        private val binding: ServiceRequestListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(serviceRequest: ServiceRequest) {
            binding.serviceRequestItemName.text = serviceRequest.name
            binding.serviceRequestItemStatus.text = serviceRequest.status
            binding.serviceRequestItemDate.text = prettyPrintElapsed(serviceRequest.date)
        }
    }
}
