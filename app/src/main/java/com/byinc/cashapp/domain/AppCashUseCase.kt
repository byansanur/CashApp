package com.byinc.cashapp.domain

import com.byinc.cashapp.data.repository.DataRepository
import com.byinc.cashapp.domain.model.CashModel
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
    suspend operator fun invoke(startDate: String, endDate: String) : List<CashModel>? {
        val query = allCashByDate.getAllCashInByDate(startDate, endDate)
        val list = mutableListOf<CashModel>()
        query?.forEach {
            list.add(it.toCashModel())
        }
        return list
    }
}

class GetCashById @Inject constructor(
    private val cashById: DataRepository
) {
    suspend operator fun invoke(id: String) =
        cashById.getCashById(id)
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