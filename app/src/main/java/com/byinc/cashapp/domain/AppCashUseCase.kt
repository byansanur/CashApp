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
    suspend operator fun invoke(startDate: String, endDate: String) =
        allCashByDate.getAllCashInByDate(startDate, endDate)
}

class GetAllCashUseCase @Inject constructor(
    private val allCash: DataRepository
) {
    suspend operator fun invoke() =
        allCash.getAllCashIn()
}

class UpdateCashUseCase @Inject constructor(
    private val updateCash: DataRepository
) {
    suspend operator fun invoke(cashModel: CashModel) =
        updateCash.updateCashIn(cashModel.toCashEntity())
}

class DeleteAllCashUseCase @Inject constructor(
    private val deleteCash: DataRepository
) {
    suspend operator fun invoke() =
        deleteCash.deleteAllCashIn()
}

class DeleteCashByIdCase @Inject constructor(
    private val deleteCashInById: DataRepository
) {
    suspend operator fun invoke(id: Int) =
        deleteCashInById.deleteCashInById(id)
}