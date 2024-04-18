package com.byinc.cashapp.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.byinc.cashapp.data.entity.CashEntity

@Dao
interface CashEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCash(cash: CashEntity)

    @Upsert
    suspend fun upsertCash(cash: CashEntity)

    @Query("SELECT * FROM cash_entity WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAllCashByDate(startDate: String, endDate: String) : List<CashEntity>?

    @Query("SELECT * FROM cash_entity")
    suspend fun getAllCash() : List<CashEntity>?

    @Update
    suspend fun updateCashIn(cash: CashEntity)

    @Query("DELETE FROM cash_entity")
    suspend fun deleteAllCashIn()

    @Query("DELETE FROM cash_entity WHERE id=:cashId")
    suspend fun deleteCashInById(cashId: Int): Int
}