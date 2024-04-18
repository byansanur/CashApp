package com.byinc.cashapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byinc.cashapp.R
import com.byinc.cashapp.databinding.ItemListCashBinding
import com.byinc.cashapp.domain.model.CashModel
import com.byinc.cashapp.utils.BCA
import com.byinc.cashapp.utils.BNI
import com.byinc.cashapp.utils.BRI
import com.byinc.cashapp.utils.KAS
import com.byinc.cashapp.utils.MANDIRI
import com.byinc.cashapp.utils.convertDateTimeViews
import com.byinc.cashapp.utils.convertRp
import com.byinc.cashapp.utils.invisible
import com.byinc.cashapp.utils.setImageDrawableRes
import com.byinc.cashapp.utils.visible

class AdapterListCashIn: RecyclerView.Adapter<AdapterListCashIn.Holder>() {

    private var list: MutableList<CashModel> = mutableListOf()

    fun submitList(input: MutableList<CashModel>) {
        list = input
        notifyDataSetChanged()
    }
    inner class Holder(private val binding: ItemListCashBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSet: CashModel) {
            binding.apply {
                when(dataSet.source) {
                    KAS -> {
                        imgSource.invisible()
                        tvKasSource.visible()
                    }
                    BCA -> imgSource.setImageDrawableRes(R.drawable.logo_bca_bank_small)
                    BNI -> imgSource.setImageDrawableRes(R.drawable.logo_bank_bni_small)
                    BRI -> imgSource.setImageDrawableRes(R.drawable.logo_bank_bri_small)
                    MANDIRI -> imgSource.setImageDrawableRes(R.drawable.logo_mandiri_bank_small)
                }
                tvAmount.text = convertRp(dataSet.amount.toInt())
                tvFromSource.text = StringBuilder("From: ${dataSet.fromSource}")
                val dateTime = StringBuilder("${dataSet.date} ${dataSet.time}")
                tvDateTime.text = convertDateTimeViews(dateTime.toString())
                root.setOnClickListener {
                    onItemClickListener?.invoke(dataSet)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemListCashBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val current = list[position]
        holder.bind(current)
    }

    private var onItemClickListener: ((CashModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (CashModel) -> Unit) {
        onItemClickListener = listener
    }

}