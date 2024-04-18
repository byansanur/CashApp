package com.byinc.cashapp.di

import android.content.Context
import androidx.room.Room
import com.byinc.cashapp.data.AppDatabase
import com.byinc.cashapp.data.repository.CashEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "cash.db"
        ).build()
    }

    @Provides
    fun cashDao(database: AppDatabase) : CashEntityDao = database.cashEntityDao()
}