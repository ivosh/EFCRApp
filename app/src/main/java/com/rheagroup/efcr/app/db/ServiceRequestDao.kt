package com.rheagroup.efcr.app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rheagroup.efcr.app.model.ServiceRequest

@Dao
interface ServiceRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg serviceRequests: ServiceRequest)

    @Update
    fun updateAll(vararg serviceRequests: ServiceRequest)

    @Delete
    fun delete(serviceRequest: ServiceRequest)

    @Query("SELECT * FROM service_requests")
    fun loadAll(): LiveData<List<ServiceRequest>>
}
