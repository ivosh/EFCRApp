package com.rheagroup.efcr.app.di

import android.content.Context
import com.rheagroup.efcr.app.network.TokenProvider
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
object TokenModule {

    @Singleton
    @Provides
    fun provideTokenProvider(@ApplicationContext context: Context): TokenProvider =
        TokenProvider(context)
}
