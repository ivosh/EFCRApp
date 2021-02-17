package com.rheagroup.efcr.app.repository

import androidx.lifecycle.LiveData
import com.rheagroup.efcr.app.db.AppDatabase
import com.rheagroup.efcr.app.db.ServiceRequestDao
import com.rheagroup.efcr.app.model.ServiceRequest
import javax.inject.Inject

class ServiceRequestRepository @Inject constructor(db: AppDatabase) {
    private val serviceRequestDao: ServiceRequestDao = db.serviceRequestDao()

    fun loadServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequestDao.loadAll()
        // :TODO: mix in Resource
    }
}