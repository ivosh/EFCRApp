package com.rheagroup.efcr.app.ui.servicerequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rheagroup.efcr.app.repository.ServiceRequestRepository
import com.rheagroup.efcr.app.vo.ServiceRequest

class ServiceRequestListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ServiceRequestRepository(application) // :TODO: Use Dependency Injection

    private val serviceRequests: LiveData<List<ServiceRequest>> = loadAllServiceRequests()

    fun getServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequests
    }

    private fun loadAllServiceRequests(): LiveData<List<ServiceRequest>> {
        return repository.loadServiceRequests()
    }
}
