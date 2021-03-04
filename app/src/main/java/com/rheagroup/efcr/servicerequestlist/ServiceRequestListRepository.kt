package com.rheagroup.efcr.servicerequestlist

import com.rheagroup.efcr.app.di.IoDispatcher
import com.rheagroup.efcr.app.network.ApiResponse
import com.rheagroup.efcr.app.network.ResourceStatus
import com.rheagroup.efcr.app.network.apiCall
import com.rheagroup.efcr.servicerequestlist.data.Customer
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import com.rheagroup.efcr.servicerequestlist.local.ServiceRequestListDao
import com.rheagroup.efcr.servicerequestlist.network.ServiceRequestData
import com.rheagroup.efcr.servicerequestlist.network.ServiceRequestListApi
import com.rheagroup.efcr.servicerequestlist.network.ServiceRequestResponse
import com.rheagroup.efcr.util.LocalDateTimeConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServiceRequestListRepository @Inject constructor(
    private val localDao: ServiceRequestListDao,
    private val remoteApi: ServiceRequestListApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val localDateTimeConverter = LocalDateTimeConverter()

    @ExperimentalCoroutinesApi
    fun getServiceRequests(): Flow<List<ServiceRequest>> {
        return localDao.loadAllSortedByDate()
            .flowOn(ioDispatcher) // Affects upstream (above) operators.
            .conflate() // Return the latest values.
    }

    suspend fun fetchServiceRequests(): ResourceStatus {
        return withContext(ioDispatcher) {
            val response = apiCall { remoteApi.getServiceRequestList() }
            if (response.kind == ApiResponse.Kind.SUCCESS) {
                val serviceRequestResponses = response.data!!.content
                serviceRequestResponses.map { extractServiceRequest(it) }.run {
                    localDao.insertAll(this)
                }
            }
            response.toResourceStatus()
        }
    }

    private fun extractServiceRequest(response: ServiceRequestResponse): ServiceRequest {
        val serviceRequestData: ServiceRequestData =
            (response.initialServiceRequestData ?: response.proposals?.get(0)?.serviceRequestData)!!
        val customer = with(serviceRequestData.customer) { Customer(id, name, surname) }

        return ServiceRequest(
            id = response.id,
            name = serviceRequestData.name,
            date = localDateTimeConverter.toLocalDateTime(response.created), // :TODO: Use a different formatter that works with milliseconds. Better: annotate ServiceRequestResponse with that formatter.
            status = response.serviceRequestStatus.status,
            customer = customer,
            null
        )
    }
}
