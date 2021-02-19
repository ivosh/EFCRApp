package com.rheagroup.efcr.servicerequestlist.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceRequestListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg serviceRequests: ServiceRequest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(serviceRequests: List<ServiceRequest>)

    @Update
    fun updateAll(vararg serviceRequests: ServiceRequest)

    @Delete
    fun delete(serviceRequest: ServiceRequest)

    @Query("SELECT * FROM service_requests ORDER BY date DESC")
    fun loadAllSortedByDate(): Flow<List<ServiceRequest>>
}
