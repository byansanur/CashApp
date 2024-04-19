package com.byinc.cashapp.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byinc.cashapp.data.entity.CashEntity

@Dao
interface CashEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCash(cash: CashEntity)

    @Query("SELECT * FROM cash_entity WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAllCashByDate(startDate: String, endDate: String) : List<CashEntity>?

    @Query("SELECT * FROM cash_entity WHERE id = :id")
    suspend fun getCashById(id: String) : CashEntity?

    @Query("UPDATE cash_entity " +
            "SET source = :source, " +
            "fromSource = :fromSource, " +
            "amount = :amount, " +
            "note = :note, " +
            "date = :date, " +
            "time = :time " +
            "WHERE id =:id")
    suspend fun updateCashInById(id: String, source: String, fromSource: String, amount: String, note: String, date: String, time: String)

    @Query("DELETE FROM cash_entity WHERE id=:cashId")
    suspend fun deleteCashInById(cashId: String)
}