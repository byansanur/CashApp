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
}