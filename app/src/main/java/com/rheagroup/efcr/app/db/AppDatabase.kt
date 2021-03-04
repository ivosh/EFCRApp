package com.rheagroup.efcr.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rheagroup.efcr.servicerequestdetail.local.ServiceRequestDetailDao
import com.rheagroup.efcr.servicerequestlist.data.Customer
import com.rheagroup.efcr.servicerequestlist.data.ProvidedBy
import com.rheagroup.efcr.servicerequestlist.data.ProvidedByConverter
import com.rheagroup.efcr.servicerequestlist.data.Provider
import com.rheagroup.efcr.servicerequestlist.data.ServiceRequest
import com.rheagroup.efcr.servicerequestlist.local.ServiceRequestListDao
import com.rheagroup.efcr.util.LocalDateTimeConverter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

@Database(
    entities = [ServiceRequest::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProvidedByConverter::class, LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "E-FCR.db"
    }

    abstract fun serviceRequestDetailDao(): ServiceRequestDetailDao
    abstract fun serviceRequestListDao(): ServiceRequestListDao
}

fun fillDatabase(database: AppDatabase) {
    GlobalScope.launch {
        database.serviceRequestListDao().insertAll(*SERVICE_REQUESTS)
    }
}

val CUSTOMER_1 = Customer(UUID.randomUUID().toString(), "Kaarel", "Hanson")
val CUSTOMER_2 = Customer(UUID.randomUUID().toString(), "Custo", "Mer")

val PROVIDER_1 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "RHEA Group")
val PROVIDER_2 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "Guardtime")
val PROVIDER_3 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "VisionSpace")
val PROVIDER_4 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "Exprivia")

val SERVICE_REQUESTS = arrayOf(
    ServiceRequest(
        UUID.randomUUID().toString(),
        "Custom Service Request for Training",
        LocalDateTime.parse("2021-02-16T18:50:03"),
        "DRAFT",
        CUSTOMER_1,
        PROVIDER_1
    ),
    ServiceRequest(
        UUID.randomUUID().toString(),
        "Another custom SR for PenTesting",
        LocalDateTime.parse("2021-02-16T18:49:12"),
        "SUBMITTED",
        CUSTOMER_1,
        PROVIDER_2
    ),
    ServiceRequest(
        UUID.randomUUID().toString(),
        "Request for Military R&D",
        LocalDateTime.parse("2021-02-16T12:53:17"),
        "NEGOTIATING",
        CUSTOMER_2,
        PROVIDER_1
    ),
    ServiceRequest(
        UUID.randomUUID().toString(),
        "My SR",
        LocalDateTime.parse("2021-02-16T07:49:38"),
        "NEGOTIATING",
        CUSTOMER_1,
        PROVIDER_3
    ),
    ServiceRequest(
        UUID.randomUUID().toString(),
        "Give me bananas!",
        LocalDateTime.parse("2021-02-15T14:52:39"),
        "SIGNED",
        CUSTOMER_1,
        PROVIDER_4
    ),
    ServiceRequest(
        UUID.randomUUID().toString(),
        "Feel free to organize me",
        LocalDateTime.parse("2021-01-30T09:00:15"),
        "ACTIVE",
        CUSTOMER_2,
        PROVIDER_2
    )
)
