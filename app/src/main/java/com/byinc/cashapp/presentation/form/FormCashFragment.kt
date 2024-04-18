package com.byinc.cashapp.presentation.form

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.byinc.cashapp.base.BaseFragment
import com.byinc.cashapp.databinding.FragmentFormCashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormCashFragment : BaseFragment<FragmentFormCashBinding>() {

    private val args: FormCashFragmentArgs by navArgs()

    private var isEdit = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEdit = args.isEdit

    }
}