package com.rheagroup.efcr.app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.rheagroup.efcr.app.db.AppDatabase
import com.rheagroup.efcr.app.db.ServiceRequestDao
import com.rheagroup.efcr.app.model.ServiceRequest

class ServiceRequestRepository(context: Context) {
    private val db: AppDatabase = AppDatabase.get(context)
    private val serviceRequestDao: ServiceRequestDao = db.serviceRequestDao()

    fun loadServiceRequests(): LiveData<List<ServiceRequest>> {
        return serviceRequestDao.loadAll()
        // :TODO: mix in Resource
    }
}