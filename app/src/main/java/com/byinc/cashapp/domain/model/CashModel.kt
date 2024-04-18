package com.byinc.cashapp.domain.model

import com.byinc.cashapp.data.entity.CashEntity

data class CashModel(
    var id: String,
    var name: String,
    var fromSource: String,
    var amount: String,
    var note: String,
    var date: String,
    var time: String
) {
    fun toCashEntity() = CashEntity(
        id = this.id,
        name = this.name,
        fromSource = this.fromSource,
        amount = this.amount,
        note = this.note,
        date = this.date,
        time = this.time
    )
}
