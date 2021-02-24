package com.rheagroup.efcr.servicerequestdetail.local

import androidx.room.Dao
import androidx.room.Query
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceRequestDetailDao {
    @Query("SELECT * FROM service_requests WHERE id = :id ")
    fun getById(id: String): Flow<ServiceRequest>
}
