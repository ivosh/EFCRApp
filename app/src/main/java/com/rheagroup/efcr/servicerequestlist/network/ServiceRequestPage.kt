package com.rheagroup.efcr.servicerequestlist.network

data class ServiceRequestPage(
    val content: List<ServiceRequestResponse>,
    val hasNext: Boolean,
    val numberOfTotalElements: Int,
    val numberOfTotalPages: Int,
    val page: Int
)
