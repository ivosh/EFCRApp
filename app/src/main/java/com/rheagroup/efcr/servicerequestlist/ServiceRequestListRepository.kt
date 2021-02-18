package com.rheagroup.efcr.servicerequestlist

import androidx.lifecycle.LiveData
import com.rheagroup.efcr.app.db.AppDatabase
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import com.rheagroup.efcr.servicerequestlist.local.ServiceRequestListDao
import javax.inject.Inject

class ServiceRequestListRepository @Inject constructor(db: AppDatabase) {
    private val serviceRequestListDao: ServiceRequestListDao = db.serviceRequestListDao()

    fun loadServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequestListDao.loadAll()
        // :TODO: mix in Resource
    }
}