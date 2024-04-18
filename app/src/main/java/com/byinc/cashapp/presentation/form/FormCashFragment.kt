package com.byinc.cashapp.presentation.form

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.byinc.cashapp.base.BaseFragment
import com.byinc.cashapp.databinding.FragmentFormCashBinding
import com.byinc.cashapp.domain.model.CashModel
import com.byinc.cashapp.utils.Resources
import com.byinc.cashapp.utils.convertDate
import com.byinc.cashapp.utils.convertDateViews
import com.byinc.cashapp.utils.convertRp
import com.byinc.cashapp.utils.convertTime
import com.byinc.cashapp.utils.convertTimeViews
import com.byinc.cashapp.utils.pad
import com.byinc.cashapp.utils.showDialogLoadingLogo
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

@AndroidEntryPoint
class FormCashFragment : BaseFragment<FragmentFormCashBinding>() {

    private val args: FormCashFragmentArgs by navArgs()
    private val viewModelForm: ViewModelForm by viewModels()
    private var isEdit = true

    private var dateTransaction = ""
    private var timeTransaction = ""
    private var from = ""
    private var source = ""
    private var amount = ""
    private var note = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        listener()
    }

    private fun initData() {
        isEdit = args.isEdit
        binding.apply {
            if (!isEdit) {
                val date = Date(System.currentTimeMillis())
                tieDate.text = convertDateViews(date)
                tieTime.text = convertTimeViews(date)
                dateTransaction = convertDate(date)
                timeTransaction = convertTime(date)

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {
        binding.apply {

            tieDate.setOnClickListener { showDatePicker() }
            tieTime.setOnClickListener { showTimePicker() }

            tieFrom.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    from = it.toString()
                }
                Log.e("TAG", "listener: from: $from", )
            }

            tieNote.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    note = it.toString()
                }
                Log.e("TAG", "listener: note: $note", )
            }

            tieAmount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    tieAmount.removeTextChangedListener(this)
                    if (!s.isNullOrEmpty()) {
                        val str = s.toString().replace("Rp", "").replace(".", "")
                        if (str.isNotEmpty()) {
                            if (str.startsWith("0")) tieAmount.setText("")
                            else {
                                amount = tieAmount.text.toString()
                                Log.e("TAG", "afterTextChanged: amount: $amount", )
                                tieAmount.setText(
                                    convertRp(str.toInt()).replace("Rp","")
                                )
                                tieAmount.setSelection(
                                    tieAmount.text.toString().length
                                )
                            }
                        } else {
                            amount = ""
                            tieAmount.setText("")
                        }
                        tieAmount.addTextChangedListener(this)
                    } else {
                        amount = ""
                        tieAmount.text = null
                        tieAmount.addTextChangedListener(this)

                    }
                }

            })

            btnSave.setOnClickListener {
                if (!isEdit) {
                    insertData()
                } else updateDataById()
            }

        }
    }

    private fun showTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setInputMode(INPUT_MODE_CLOCK)
            .setTitleText("Select transaction time")
            .build()
        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            timeTransaction = "${pad(hour)}:${pad(minute)}"
            binding.tieTime.text = timeTransaction
        }
        if (!timePicker.isAdded)
            timePicker.show(parentFragmentManager, "TIME_TRANSACTION")
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
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setInputMode(INPUT_MODE_CALENDAR)
            .setTitleText("Select transaction date")
            .setCalendarConstraints(calendarConstraint)
            .build()
        datePicker.addOnNegativeButtonClickListener { datePicker.dismiss() }
        datePicker.addOnPositiveButtonClickListener {
            val timeZoneUTC: TimeZone = TimeZone.getDefault()
            // It will be negative, so that's the -1
            val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1
            val date = Date(it + offsetFromUTC)

            dateTransaction = convertDate(date)
            binding.tieDate.text = convertDateViews(date)
            binding.tieDate.isFocusable = false
        }
        if (!datePicker.isAdded)
            datePicker.show(parentFragmentManager, "DATE_TRANSACTION")
    }

    private fun updateDataById() {

    }

    private fun insertData() {
        source = selectionSourceChip()
        if (validInput()) {
            val cashModel = CashModel(
                id = UUID.randomUUID().toString(),
                source = source,
                fromSource = from,
                amount = amount.replace(".",""),
                note = note,
                date = dateTransaction,
                time = timeTransaction
            )
            viewModelForm.insertData(cashModel).observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> showDialogLoadingLogo(requireContext(), layoutInflater)
                    is Resources.Success -> {
                        if (it.data == true) {
                            Toast.makeText(requireContext(), "Nice to your added cash!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resources.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Check your input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validInput() : Boolean {
        return when {
            dateTransaction.isNotEmpty() -> true
            timeTransaction.isNotEmpty() -> true
            from.isNotEmpty() -> true
            source.isNotEmpty() -> true
            amount.isNotEmpty() -> true
            else -> false
        }
    }

    private fun selectionSourceChip(): String {
        return binding.cgSource.children.toList()
            .filter { (it as Chip).isChecked }
            .joinToString { (it as Chip).text }
    }
}