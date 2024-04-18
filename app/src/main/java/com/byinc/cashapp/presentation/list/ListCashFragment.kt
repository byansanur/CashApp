package com.byinc.cashapp.presentation.list

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.byinc.cashapp.base.BaseFragment
import com.byinc.cashapp.databinding.FragmentListCashBinding
import com.byinc.cashapp.domain.model.CashModel
import com.byinc.cashapp.utils.Resources
import com.byinc.cashapp.utils.convertDate
import com.byinc.cashapp.utils.convertDateViews
import com.byinc.cashapp.utils.gone
import com.byinc.cashapp.utils.showDialogLoadingLogo
import com.byinc.cashapp.utils.visible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class ListCashFragment : BaseFragment<FragmentListCashBinding>() {

    private val viewModelList : ViewModelList by viewModels()

    private var startDate = ""
    private var endDate = ""

    private lateinit var adapterListCashIn: AdapterListCashIn
    private var mutableCashModel: MutableList<CashModel> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        listener()
        getDataListByDate(startDate, endDate)
    }

    private fun initView() {
        adapterListCashIn = AdapterListCashIn()

        val date = Date(System.currentTimeMillis())
        startDate = convertDate(date)
        endDate = convertDate(date)

        binding.apply {
            tvSelectStartDate.text = convertDateViews(date)
            tvSelectEndDate.text = convertDateViews(date)
        }
    }

    private fun listener() {
        binding.apply {
            fabAddCash.setOnClickListener {
                val nav = ListCashFragmentDirections
                    .actionListCashFragmentToFormCashFragment(isEdit = false, idWantToEdit = null)
                findNavController().navigate(nav)
            }
            tvSelectStartDate.setOnClickListener { showDatePicker() }

            adapterListCashIn.setOnItemClickListener {
                val nav = ListCashFragmentDirections
                    .actionListCashFragmentToFormCashFragment(isEdit = true, idWantToEdit = it.id)
                findNavController().navigate(nav)
            }
        }
    }

    private fun showDatePicker() {
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
            val dateFirst = Date(it.first + offsetFromUTC)
            val dateSecond = Date(it.second + offsetFromUTC)

            binding.tvSelectStartDate.text = convertDateViews(dateFirst)
            startDate = convertDate(dateFirst)

            binding.tvSelectEndDate.text = convertDateViews(dateSecond)
            endDate = convertDate(dateSecond)

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
                            mutableCashModel.clear()
                            for (i in 0 until it.data.size) {
                                mutableCashModel.add(it.data[i])
                                adapterListCashIn.submitList(mutableCashModel)
                            }
                            rvListCash.adapter = adapterListCashIn
                            rvListCash.layoutManager = LinearLayoutManager(requireContext())
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

}