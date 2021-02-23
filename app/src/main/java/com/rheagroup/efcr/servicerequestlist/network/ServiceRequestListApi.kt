package com.rheagroup.efcr.servicerequestlist.network

import retrofit2.http.GET

interface ServiceRequestListApi {
    @GET("/api/v1/service-requests?limit=1000")
    suspend fun getServiceRequestList(): ServiceRequestPage
}
