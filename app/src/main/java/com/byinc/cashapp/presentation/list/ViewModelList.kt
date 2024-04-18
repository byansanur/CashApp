package com.byinc.cashapp.presentation.list

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
class ViewModelList @Inject constructor(
    private val domainRepository: DomainRepository
) : ViewModel() {

    fun getAllListCash() : LiveData<Resources<List<CashModel>>> {
        val output = MutableLiveData<Resources<List<CashModel>>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)
            val cashModel = domainRepository.getAllCashIn()
            if (cashModel.isNullOrEmpty()) {
                output.postValue(Resources.Error("No Data Found!"))
            } else {
                cashModel.forEach {
                    output.postValue(Resources.Success(listOf(it.toCashModel())))
                }
            }
        }
        return output
    }

    fun getAllListCashByDate(startDate: String, endDate: String) : LiveData<Resources<List<CashModel>>> {
        val output = MutableLiveData<Resources<List<CashModel>>>()
        viewModelScope.launch {
            output.postValue(Resources.Loading())
            delay(2000)
            val cashModel = domainRepository.getAllCashInByDate(startDate, endDate)
            if (cashModel.isNullOrEmpty()) {
                output.postValue(Resources.Error("No Data Found!"))
            } else {
                output.postValue(Resources.Success(cashModel))
            }
        }
        return output
    }


}