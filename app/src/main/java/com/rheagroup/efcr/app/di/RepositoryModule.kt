package com.rheagroup.efcr.app.di

import com.rheagroup.efcr.app.db.AppDatabase
import com.rheagroup.efcr.servicerequestlist.ServiceRequestListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideServiceRequestRepository(db: AppDatabase): ServiceRequestListRepository {
        return ServiceRequestListRepository(db)
    }
}
