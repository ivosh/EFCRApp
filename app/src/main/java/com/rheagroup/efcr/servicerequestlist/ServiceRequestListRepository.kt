package com.rheagroup.efcr.servicerequestlist

import com.rheagroup.efcr.app.di.IoDispatcher
import com.rheagroup.efcr.util.LocalDateTimeConverter
import com.rheagroup.efcr.app.network.Resource
import com.rheagroup.efcr.app.network.Status
import com.rheagroup.efcr.servicerequestlist.data.Customer
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import com.rheagroup.efcr.servicerequestlist.local.ServiceRequestListDao
import com.rheagroup.efcr.servicerequestlist.network.ServiceRequestListApi
import com.rheagroup.efcr.servicerequestlist.network.ServiceRequestResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

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

    suspend fun fetchServiceRequests(): Status {
        return withContext(ioDispatcher) {
            val result = apiCall() { remoteApi.getServiceRequestList() }
            if (result.status == Status.SUCCESS) {
                val serviceRequestResponses = result.data!!.content
                serviceRequestResponses.map { extractServiceRequest(it) }.run {
                    localDao.insertAll(this)
                }
            }
            result.status
        }
    }

    private suspend fun <T> apiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.success(apiCall())
        } catch (exception: Exception) {
            Resource.error(
                exception.message ?: "<no error given>",
                null
            ) // :TODO: Have some classification of errors (network, http, auth) and pass it to the view
        }
    }

    private fun extractServiceRequest(response: ServiceRequestResponse): ServiceRequest {
        val customer =
            Customer(response.customer.id, response.customer.name, response.customer.surname)
        return ServiceRequest(
            id = response.id,
            name = response.name,
            date = localDateTimeConverter.toLocalDateTime(response.created), // :TODO: Use a different formatter that works with milliseconds. Better: annotate ServiceRequestResponse with that formatter.
            status = response.serviceRequestStatus.status,
            customer = customer,
            null
        )
    }
}