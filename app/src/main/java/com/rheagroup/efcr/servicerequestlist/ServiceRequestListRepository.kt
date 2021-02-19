package com.rheagroup.efcr.servicerequestlist

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ServiceRequestListRepository @Inject constructor(
    private val localDao: ServiceRequestListDao,
    private val remoteApi: ServiceRequestListApi
) {
    private val localDateTimeConverter = LocalDateTimeConverter()

    @ExperimentalCoroutinesApi
    fun getServiceRequests(): Flow<List<ServiceRequest>> {
        return localDao.loadAllSortedByDate()
            .flowOn(Dispatchers.Default)
            .conflate() // Return the latest values.
    }

    suspend fun fetchServiceRequests(): Status {
        val result = apiCall(Dispatchers.IO) { remoteApi.getServiceRequestList() }
        if (result.status == Status.SUCCESS) {
            val serviceRequestResponses = result.data!!.content
            serviceRequestResponses.map { extractServiceRequest(it) }.run {
                localDao.insertAll(this)
            }
        }
        return result.status
    }

    private suspend fun <T> apiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.success(apiCall())
            } catch (exception: Exception) {
                Resource.error(exception.message ?: "<no error given>", null) // :TODO: Have some classification of errors (network, http, auth) and pass it to the view
            }
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