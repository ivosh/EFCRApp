package com.rheagroup.efcr.app.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime

@Entity(tableName = "service_requests")
data class ServiceRequest(
        @PrimaryKey val id: String, // is UUID
        val name: String,
        val date: LocalDateTime,
        val status: String,
        @Embedded(prefix = "customer_") val customer: Customer,
        @Embedded(prefix = "provider_") val provider: Provider
)

data class Customer(
        val id: String, // is UUID
        val name: String
)

enum class ProvidedBy(val providedBy: String) {
    SINGLE_PROVIDER("SINGLE_PROVIDER"),
    FEDERATION("FEDERATION")
}

data class Provider(
        @ColumnInfo(name = "provided_by")
        val providedBy: ProvidedBy,

        val id: String, // is UUID

        val name: String
)

class ProvidedByConverter {
    @TypeConverter
    fun fromProvidedBy(providedBy: ProvidedBy) = providedBy.providedBy

    @TypeConverter
    fun toProvidedBy(providedBy: String) = ProvidedBy.valueOf(providedBy)
}
