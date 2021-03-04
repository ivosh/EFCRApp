package com.rheagroup.efcr.servicerequestlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.rheagroup.efcr.app.network.ResourceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class ServiceRequestListViewModel @Inject constructor(
    private val repository: ServiceRequestListRepository
) :
    ViewModel() {

    private val triggerFetchServiceRequests = MutableLiveData<Int>()
    private val atomicInteger = AtomicInteger()

    @ExperimentalCoroutinesApi
    val serviceRequests = repository.getServiceRequests().asLiveData()

    fun fetchServiceRequests() {
        triggerFetchServiceRequests.value = atomicInteger.incrementAndGet()
    }

    val fetchStatus = triggerFetchServiceRequests.switchMap {
        liveData {
            emit(ResourceStatus.loading())
            emit(repository.fetchServiceRequests())
        }
    }
}
