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
    suspend operator fun invoke(id: String) : CashModel? =
        cashById.getCashById(id)?.toCashModel()
}

class GetAllCashUseCase @Inject constructor(
    private val allCash: DataRepository
) {
    suspend operator fun invoke() =
        allCash.getAllCashIn()
}

class UpdateCashInById @Inject constructor(
    private val updateCashInById: DataRepository
) {
    suspend operator fun invoke(id: String, source: String, fromSource: String, amount: String, note: String, date: String, time: String) =
        updateCashInById.updateCashInById(id, source, fromSource, amount, note, date, time)
}

class DeleteCashByIdCase @Inject constructor(
    private val deleteCashInById: DataRepository
) {
    suspend operator fun invoke(id: String) =
        deleteCashInById.deleteCashInById(id)
}