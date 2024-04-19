package com.byinc.cashapp.presentation.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byinc.cashapp.domain.DomainRepository
import com.byinc.cashapp.domain.model.CashModel
import com.byinc.cashapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelForm @Inject constructor(
    private val domainRepository: DomainRepository
) : ViewModel() {

    fun insertData(cashModel: CashModel?) : LiveData<Resources<Boolean>> {
        val output = MutableLiveData<Resources<Boolean>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)
            if (cashModel != null) {
                domainRepository.insertCashIn(cashModel)
                output.postValue(Resources.Success(true))
            } else {
                output.postValue(Resources.Error("Please check your data again!"))
            }
        }
        return output
    }

    fun getCashById(id: String) : LiveData<Resources<CashModel>> {
        val output = MutableLiveData<Resources<CashModel>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)
            val getCashId = domainRepository.getCashById(id)
            if (getCashId != null) {
                output.postValue(Resources.Success(getCashId))
            } else {
                output.postValue(Resources.Error("Cash not found!"))
            }
        }
        return output
    }

    fun upsertCash(cashModel: CashModel?) : LiveData<Resources<Boolean>> {
        val output = MutableLiveData<Resources<Boolean>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)

            if (cashModel?.id != null) {
                domainRepository.updateCashInById(
                    cashModel.id,
                    cashModel.source,
                    cashModel.fromSource,
                    cashModel.amount,
                    cashModel.note,
                    cashModel.date,
                    cashModel.time
                )
                output.postValue(Resources.Success(true))
            } else {
                output.postValue(Resources.Error("Please check your data again!"))
            }
        }
        return output
    }

    fun deleteById(id: String?) : LiveData<Resources<Boolean>> {
        val output = MutableLiveData<Resources<Boolean>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)
            if (!id.isNullOrEmpty()) {
                domainRepository.deleteCashById(id)
                output.postValue(Resources.Success(true))
            } else {
                output.postValue(Resources.Error("Error deleting cash"))
            }
        }
        return output
    }
}