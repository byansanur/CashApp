package com.byinc.cashapp.domain

import com.byinc.cashapp.domain.model.CashModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainRepository @Inject constructor(
    private val addCashInUseCase: AddCashInUseCase,
    private val getAllCashUseCase: GetAllCashUseCase,
    private val getAllCashByDateUseCase: GetAllCashByDateUseCase,
    private val getCashById: GetCashById,
    private val deleteCashByIdCase: DeleteCashByIdCase,
    private val updateCashInById: UpdateCashInById
){
    suspend fun insertCashIn(cashModel: CashModel) =
        addCashInUseCase.invoke(cashModel)

    suspend fun getAllCashIn() =
        getAllCashUseCase.invoke()

    suspend fun getAllCashInByDate(startDate: String, endDate: String) =
        getAllCashByDateUseCase.invoke(startDate, endDate)

    suspend fun getCashById(id: String) =
        getCashById.invoke(id)


    suspend fun deleteCashById(id: String) =
        deleteCashByIdCase.invoke(id)

    suspend fun updateCashInById(id: String, source: String, fromSource: String, amount: String, note: String, date: String, time: String) =
        updateCashInById.invoke(id, source, fromSource, amount, note, date, time)
}