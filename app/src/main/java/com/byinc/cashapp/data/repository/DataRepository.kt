package com.byinc.cashapp.data.repository

import com.byinc.cashapp.data.entity.CashEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val cashEntityDao: CashEntityDao) {

    suspend fun getAllCashIn() = cashEntityDao.getAllCash()

    suspend fun getAllCashInByDate(startDate: String, endDate: String) = cashEntityDao.getAllCashByDate(startDate, endDate)

    suspend fun getCashById(id: String) = cashEntityDao.getCashById(id)

    suspend fun insertCashIn(cashEntity: CashEntity) = cashEntityDao.insertCash(cashEntity)

    suspend fun updateCashInById(id: String, source: String, fromSource: String, amount: String, note: String, date: String, time: String) =
        cashEntityDao.updateCashIn(id, source, fromSource, amount, note, date, time)

    suspend fun deleteCashInById(id: String) = cashEntityDao.deleteCashInById(id)
}