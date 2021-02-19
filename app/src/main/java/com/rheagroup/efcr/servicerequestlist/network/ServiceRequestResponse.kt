package com.rheagroup.efcr.servicerequestlist.network

import com.rheagroup.efcr.servicerequestlist.data.Customer

data class ServiceRequestResponse(
    val id: String,
    val created: String,
    val customer: Customer,
    val name: String,
    val serviceRequestStatus: ServiceRequestStatus
)

data class ServiceRequestStatus(
    val status: String
)
