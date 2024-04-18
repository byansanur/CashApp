package com.byinc.cashapp.domain

import com.byinc.cashapp.domain.model.CashModel
import com.byinc.cashapp.data.repository.DataRepository
import javax.inject.Inject

class AddCashInUseCase @Inject constructor(
    private val addCashIn: DataRepository
) {
    suspend operator fun invoke(cashModel: CashModel) =
        addCashIn.insertCashIn(cashModel.toCashEntity())
}

class GetAllCashByDateUseCase @Inject constructor(
    private val allCashByDate: DataRepository
) {
    suspend operator fun invoke(date: String) =
        allCashByDate.getAllCashInByDate(date)
}

class GetAllCashUseCase @Inject constructor(
    private val allCash: DataRepository
) {
    suspend operator fun invoke() = allCash.getAllCashIn()
}

class UpdateCashUseCase @Inject constructor(
    private val updateCash: DataRepository
) {
    suspend operator fun invoke(cashModel: CashModel) =
        updateCash.updateCashIn(cashModel.toCashEntity())
}

class DeleteCashUseCase @Inject constructor(
    private val deleteCash: DataRepository
) {
    suspend operator fun invoke() = deleteCash.deleteAllCashIn()
}