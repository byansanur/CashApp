package com.byinc.cashapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.byinc.cashapp.data.entity.CashEntity
import com.byinc.cashapp.data.repository.CashEntityDao

@Database(entities = [CashEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cashEntityDao(): CashEntityDao
}