package com.rheagroup.efcr.servicerequestdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi

@HiltViewModel
class ServiceRequestDetailViewModel @Inject constructor(
    private val repository: ServiceRequestDetailRepository
) :
    ViewModel() {

    @ExperimentalCoroutinesApi
    fun getServiceRequest(id: String) = repository.getServiceRequest(id).asLiveData()
}
