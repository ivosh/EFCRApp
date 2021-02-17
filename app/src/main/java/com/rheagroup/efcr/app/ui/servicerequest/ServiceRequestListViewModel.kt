package com.rheagroup.efcr.app.ui.servicerequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rheagroup.efcr.app.repository.ServiceRequestRepository
import com.rheagroup.efcr.app.model.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceRequestListViewModel @Inject constructor(private val repository: ServiceRequestRepository) : ViewModel() {
    private val serviceRequests: LiveData<List<ServiceRequest>> = loadAllServiceRequests()

    fun getServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequests
    }

    private fun loadAllServiceRequests(): LiveData<List<ServiceRequest>> {
        return repository.loadServiceRequests()
    }
}
