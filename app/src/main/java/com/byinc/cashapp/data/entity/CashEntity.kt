package com.byinc.cashapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.byinc.cashapp.domain.model.CashModel

@Entity(tableName = "cash_entity")
data class CashEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "source")
    val source: String,
    @ColumnInfo(name = "fromSource")
    val fromSource: String,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String
) {
    fun toCashModel() = CashModel(
        id = this.id,
        source = this.source,
        fromSource = this.fromSource,
        amount = this.amount,
        note = this.note,
        date = this.date,
        time = this.time
    )
}
