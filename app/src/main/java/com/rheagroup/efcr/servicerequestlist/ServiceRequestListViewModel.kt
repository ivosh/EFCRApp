package com.rheagroup.efcr.servicerequestlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceRequestListViewModel @Inject constructor(private val repository: ServiceRequestListRepository) :
    ViewModel() {
    private val serviceRequests: LiveData<List<ServiceRequest>> = loadAllServiceRequests()

    fun getServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequests
    }

    private fun loadAllServiceRequests(): LiveData<List<ServiceRequest>> {
        return repository.loadServiceRequests()
    }
}
