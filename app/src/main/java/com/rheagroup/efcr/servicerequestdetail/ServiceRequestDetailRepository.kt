package com.rheagroup.efcr.servicerequestdetail

import com.rheagroup.efcr.app.di.IoDispatcher
import com.rheagroup.efcr.servicerequestdetail.local.ServiceRequestDetailDao
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ServiceRequestDetailRepository @Inject constructor(
    private val localDao: ServiceRequestDetailDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    @ExperimentalCoroutinesApi
    fun getServiceRequest(id: String): Flow<ServiceRequest> {
        return localDao.getById(id)
            .flowOn(ioDispatcher) // Affects upstream (above) operators.
            .conflate() // Return the latest values.
    }
}
