package com.rheagroup.efcr.servicerequestlist.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest

@Dao
interface ServiceRequestListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg serviceRequests: ServiceRequest)

    @Update
    fun updateAll(vararg serviceRequests: ServiceRequest)

    @Delete
    fun delete(serviceRequest: ServiceRequest)

    @Query("SELECT * FROM service_requests")
    fun loadAll(): LiveData<List<ServiceRequest>>
}
