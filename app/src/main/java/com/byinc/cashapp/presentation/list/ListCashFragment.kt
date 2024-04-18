package com.byinc.cashapp.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.byinc.cashapp.base.BaseFragment
import com.byinc.cashapp.databinding.FragmentListCashBinding
import com.byinc.cashapp.utils.Resources
import com.byinc.cashapp.utils.gone
import com.byinc.cashapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCashFragment : BaseFragment<FragmentListCashBinding>() {

    private val viewModelList : ViewModelList by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataList()
    }

    private fun getDataList() {
        viewModelList.getAllListCash().observe(viewLifecycleOwner) {
            when(it) {
                is Resources.Loading -> {
                    Log.e("TAG", "onViewCreated: loading")
                }
                is Resources.Success -> {
                    Log.e("TAG", "onViewCreated: success: ${it.data}")
                    binding.apply {
                        if (it.data.isNullOrEmpty()) {
                            rvListCash.gone()
                            includedMsgLayout.root.visible()
                        } else {
                            rvListCash.visible()
                            includedMsgLayout.root.gone()
                        }
                    }
                }
                is Resources.Error -> {
                    binding.apply {
                        rvListCash.gone()
                        includedMsgLayout.root.visible()
                        includedMsgLayout.tvProblemInfo.text = it.message
                    }
                    Log.e("TAG", "onViewCreated: error: ${it.message}")
                }
            }
        }
    }

}