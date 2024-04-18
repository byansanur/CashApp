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
    private val updateCashUseCase: UpdateCashUseCase,
    private val deleteAllCashUseCase: DeleteAllCashUseCase,
    private val deleteCashByIdCase: DeleteCashByIdCase
){
    suspend fun insertCashIn(cashModel: CashModel) =
        addCashInUseCase.invoke(cashModel)

    suspend fun getAllCashIn() =
        getAllCashUseCase.invoke()

    suspend fun getAllCashInByDate(startDate: String, endDate: String) =
        getAllCashByDateUseCase.invoke(startDate, endDate)

    suspend fun getCashById(id: String) =
        getCashById.invoke(id)

    suspend fun updateCashIn(cashModel: CashModel) =
        updateCashUseCase.invoke(cashModel)

    suspend fun deleteAllCashIn() =
        deleteAllCashUseCase.invoke()

    suspend fun deleteCashById(id: Int) =
        deleteCashByIdCase.invoke(id)
}