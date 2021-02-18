package com.rheagroup.efcr.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rheagroup.efcr.app.db.AppDatabase
import com.rheagroup.efcr.app.db.AppDatabase.Companion.DATABASE_NAME
import com.rheagroup.efcr.app.db.fillDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's SingletonComponent.
 */
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    private lateinit var db: AppDatabase

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        db =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries() // :TODO: Remove this.
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(sqlDb: SupportSQLiteDatabase) {
                        super.onCreate(sqlDb)
                        db.run {
                            fillDatabase(this)
                        }

                    }
                })
                .build()
        return db
    }
}
