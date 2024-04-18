package com.byinc.cashapp.data.repository

import com.byinc.cashapp.data.entity.CashEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val cashEntityDao: CashEntityDao) {

    suspend fun getAllCashIn() = cashEntityDao.getAllCash()

    suspend fun getAllCashInByDate(date: String) = cashEntityDao.getAllCashByDate(date)

    suspend fun insertCashIn(cashEntity: CashEntity) = cashEntityDao.insertCash(cashEntity)

    suspend fun updateCashIn(cashEntity: CashEntity) = cashEntityDao.updateCashIn(cashEntity)

    suspend fun deleteAllCashIn() = cashEntityDao.deleteAllCashIn()
}