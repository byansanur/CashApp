package com.byinc.cashapp.presentation.list

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.byinc.cashapp.base.BaseFragment
import com.byinc.cashapp.databinding.FragmentListCashBinding
import com.byinc.cashapp.utils.Resources
import com.byinc.cashapp.utils.gone
import com.byinc.cashapp.utils.showDialogLoadingLogo
import com.byinc.cashapp.utils.visible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class ListCashFragment : BaseFragment<FragmentListCashBinding>() {

    private val viewModelList : ViewModelList by viewModels()

    private var startDate = ""
    private var endDate = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()
        getDataList()
    }

    private fun listener() {
        binding.apply {
            fabAddCash.setOnClickListener {
                val nav = ListCashFragmentDirections.actionListCashFragmentToFormCashFragment()
                findNavController().navigate(nav)
            }
            tvSelectStartDate.setOnClickListener { showDatePicker(true) }
//            tvSelectEndDate.setOnClickListener { showDatePicker(false) }
        }
    }

    private fun showDatePicker(isStart: Boolean) {
        val calendarEnd = Calendar.getInstance(Locale.getDefault())
        calendarEnd.add(Calendar.DATE, -1)
        val calendarStart = Calendar.getInstance(Locale.getDefault())
        calendarStart.add(Calendar.MONTH, -2)
        val calendarConstraint = CalendarConstraints.Builder()
            .setStart(calendarStart.timeInMillis)
            .setOpenAt(calendarEnd.timeInMillis)
            .setEnd(calendarEnd.timeInMillis)
            .setValidator(DateValidatorPointBackward.now())
            .build()
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setInputMode(INPUT_MODE_CALENDAR)
            .setTitleText("Select date period")
            .setCalendarConstraints(calendarConstraint)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val timeZoneUTC: TimeZone = TimeZone.getDefault()
            // It will be negative, so that's the -1
            val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1

            val simpleFormatFirst = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateFirst = Date(it.first + offsetFromUTC)
            val finaleDateFirst = simpleFormatFirst.format(dateFirst)

            val sfSecond = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateSecond = Date(it.second + offsetFromUTC)
            val finalDateSecond = sfSecond.format(dateSecond)

            binding.tvSelectStartDate.text = finaleDateFirst
            startDate = finaleDateFirst

            binding.tvSelectEndDate.text = finalDateSecond
            endDate = finalDateSecond
            getDataListByDate(startDate, endDate)
        }
        datePicker.addOnNegativeButtonClickListener { datePicker.dismiss() }
        datePicker.show(childFragmentManager, "DATE_START_END")
    }

    private fun getDataListByDate(startDate: String, endDate: String) {
        viewModelList.getAllListCashByDate(startDate, endDate).observe(viewLifecycleOwner) {
            when(it) {
                is Resources.Loading -> {
                    showDialogLoadingLogo(requireContext(), layoutInflater)
                }
                is Resources.Success -> {
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
                }
            }
        }
    }

    private fun getDataList() {
        viewModelList.getAllListCash().observe(viewLifecycleOwner) {
            when(it) {
                is Resources.Loading -> {
                    Log.e("TAG", "onViewCreated: loading")
                    showDialogLoadingLogo(requireContext(), layoutInflater)
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