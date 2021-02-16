package com.rheagroup.efcr.app.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rheagroup.efcr.app.model.Customer
import com.rheagroup.efcr.app.model.ProvidedBy
import com.rheagroup.efcr.app.model.ProvidedByConverter
import com.rheagroup.efcr.app.model.Provider
import com.rheagroup.efcr.app.model.ServiceRequest
import java.time.LocalDateTime
import java.util.*

@Database(
        entities = [ServiceRequest::class],
        version = 1,
        exportSchema = false)
@TypeConverters(ProvidedByConverter::class, LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun serviceRequestDao(): ServiceRequestDao

    /**
     * Singleton database object. :TODO: Use a Dependency Injection framework.
     */
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance!!
        }


        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "E-FCR.db")
                        .allowMainThreadQueries() // :TODO: Remove this.
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                instance?.run {
                                    fillDatabase(this)
                                }

                            }
                        })
                        .build()

        private fun fillDatabase(database: AppDatabase) {
            AsyncTask.execute { // :TODO: use coroutine to delegate this to another thread
                database.serviceRequestDao().insertAll(*SERVICE_REQUESTS)
            }
        }
    }
}

val CUSTOMER_1 = Customer(UUID.randomUUID().toString(), "Kaarel Hanson")
val CUSTOMER_2 = Customer(UUID.randomUUID().toString(), "Custo Mer")

val PROVIDER_1 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "RHEA Group")
val PROVIDER_2 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "Guardtime")
val PROVIDER_3 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "VisionSpace")
val PROVIDER_4 = Provider(ProvidedBy.SINGLE_PROVIDER, UUID.randomUUID().toString(), "Exprivia")

val SERVICE_REQUESTS = arrayOf(
        ServiceRequest(UUID.randomUUID().toString(), "Custom Service Request for Training", LocalDateTime.parse("2021-02-16T18:50:03"), "DRAFT", CUSTOMER_1, PROVIDER_1),
        ServiceRequest(UUID.randomUUID().toString(), "Another custom SR for PenTesting", LocalDateTime.parse("2021-02-16T18:49:12"), "SUBMITTED", CUSTOMER_1, PROVIDER_2),
        ServiceRequest(UUID.randomUUID().toString(), "Request for Military R&D", LocalDateTime.parse("2021-02-16T12:53:17"), "NEGOTIATING", CUSTOMER_2, PROVIDER_3),
        ServiceRequest(UUID.randomUUID().toString(), "My SR", LocalDateTime.parse("2021-02-16T07:49:38"), "NEGOTIATING", CUSTOMER_1, PROVIDER_2),
        ServiceRequest(UUID.randomUUID().toString(), "Give me bananas!", LocalDateTime.parse("2021-02-15T14:52:39"), "SIGNED", CUSTOMER_1, PROVIDER_4),
        ServiceRequest(UUID.randomUUID().toString(), "Feel free to organize me", LocalDateTime.parse("2021-01-30T09:00:15"), "ACTIVE", CUSTOMER_2, PROVIDER_3)
)
