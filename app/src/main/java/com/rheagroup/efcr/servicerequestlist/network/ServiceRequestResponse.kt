package com.rheagroup.efcr.servicerequestlist.network

import com.rheagroup.efcr.servicerequestlist.data.Customer

data class ServiceRequestResponse(
    val id: String,
    val created: String,
    val initialServiceRequestData: ServiceRequestData?,
    val proposals: List<Proposal>?,
    val serviceRequestStatus: ServiceRequestStatus
)

data class Proposal(
    val serviceRequestData: ServiceRequestData
)

data class ServiceRequestData(
    val customer: Customer,
    val name: String,
)

data class ServiceRequestStatus(
    val status: String
)
