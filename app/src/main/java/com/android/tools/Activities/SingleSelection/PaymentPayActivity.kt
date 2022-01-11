package com.android.tools.Activities.SingleSelection

import com.android.tools.PaymentListResponce
import com.android.tools.R


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import  com.android.tools.api.ApiClient
import  com.android.tools.base.BaseActivity
import  com.android.tools.base.BaseAdaptor
import  com.android.tools.model.PaymentItemModel
import  com.android.tools.utils.*
import  com.android.tools.utils.Common.alertErrorOrValidationDialog
import  com.android.tools.utils.Common.dismissLoadingProgress
import  com.android.tools.utils.Common.isCheckNetwork
import  com.android.tools.utils.Common.showErrorFullMsg
import  com.android.tools.utils.Common.showLoadingProgress
import  com.android.tools.utils.SharePreference.Companion.getStringPref
import  com.android.tools.utils.SharePreference.Companion.userId

import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.row_payment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap


class PaymentPayActivity : BaseActivity() {
    var strGetData = ""
    var logoimg = ""
    var strRezorPayKey = ""
    var listData = ArrayList<PaymentItemModel>()
    override fun setLayout(): Int {
        return R.layout.activity_payment
    }

    override fun InitView() {

        ivBack.setOnClickListener {
            finish()
        }


        if (isCheckNetwork(this@PaymentPayActivity)) {
            callApiPayment()
        } else {
            alertErrorOrValidationDialog(
                this@PaymentPayActivity,
                resources.getString(R.string.no_internet)
            )
        }
        tvPaynow.setOnClickListener {
            if (strGetData == "Wallet") {
                if (isCheckNetwork(this@PaymentPayActivity)) {

                    Toast.makeText(this, strGetData, Toast.LENGTH_LONG).show()
                } else {
                    alertErrorOrValidationDialog(
                        this@PaymentPayActivity,
                        resources.getString(R.string.no_internet)
                    )
                }

            } else if (strGetData == "COD") {
                if (isCheckNetwork(this@PaymentPayActivity)) {

                    Toast.makeText(this, strGetData, Toast.LENGTH_LONG).show()
                } else {
                    alertErrorOrValidationDialog(
                        this@PaymentPayActivity,
                        resources.getString(R.string.no_internet)
                    )
                }
            } else if (strGetData == "RazorPay") {
                Toast.makeText(this, strGetData, Toast.LENGTH_LONG).show()

            } else if (strGetData == "Stripe") {
                Toast.makeText(this, strGetData, Toast.LENGTH_LONG).show()
            } else if (strGetData == "") {
                showErrorFullMsg(
                    this@PaymentPayActivity,
                    resources.getString(R.string.payment_type_selection_error)
                )
            }
        }


    }

    private fun callApiPayment() {
        showLoadingProgress(this@PaymentPayActivity)
        val map = HashMap<String, String>()
        map["user_id"] = getStringPref(this@PaymentPayActivity, userId)!!
        val call = ApiClient.getClient.getPaymentType(map)
        call.enqueue(object : Callback<PaymentListResponce> {
            override fun onResponse(
                call: Call<PaymentListResponce>,
                response: Response<PaymentListResponce>
            ) {
                if (response.code() == 200) {
                    dismissLoadingProgress()
                    val restResponce: PaymentListResponce = response.body()!!
                    if (restResponce.status == 1) {
                        logoimg = restResponce.logo.toString()
                        val listPaymentItemModel = PaymentItemModel()
                        listPaymentItemModel.isSelect = false
                        listPaymentItemModel.environment = 0
                        listPaymentItemModel.livePublicKey = ""
                        listPaymentItemModel.testPublicKey = ""
                        listPaymentItemModel.paymentName = "Wallet"
                        listPaymentItemModel.wallet_amount = restResponce.walletamount
                        listData.add(listPaymentItemModel)
                        if (restResponce.data!!.size > 0) {
                            listData.addAll(restResponce.data)
                        }
                        setPaymentItemAdaptor(listData)
                    } else {
                        showErrorFullMsg(
                            this@PaymentPayActivity,
                            restResponce.message!!
                        )
                        val listPaymentItemModel = PaymentItemModel()
                        listPaymentItemModel.isSelect = false
                        listPaymentItemModel.environment = 0
                        listPaymentItemModel.livePublicKey = ""
                        listPaymentItemModel.testPublicKey = ""
                        listPaymentItemModel.paymentName = "Wallet"
                        listPaymentItemModel.wallet_amount = restResponce.walletamount
                        listData.add(listPaymentItemModel)
                        setPaymentItemAdaptor(listData)
                    }
                } else {
                    dismissLoadingProgress()


                }
            }

            override fun onFailure(call: Call<PaymentListResponce>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@PaymentPayActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }


    override fun onBackPressed() {
        finish()
    }


    fun setPaymentItemAdaptor(paymentItemList: ArrayList<PaymentItemModel>) {
        val orderHistoryAdapter = object : BaseAdaptor<PaymentItemModel>(
            this@PaymentPayActivity,
            paymentItemList
        ) {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("SetTextI18n")
            override fun onBindData(
                holder: RecyclerView.ViewHolder?,
                `val`: PaymentItemModel,
                position: Int
            ) {
                if (paymentItemList[position].isSelect) {
                    holder!!.itemView.llPaymentView.setBackgroundColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorPrimary,
                            null
                        )
                    )

                    holder.itemView.tvPaymentName.setTextColor(
                        ColorStateList.valueOf(
                            ResourcesCompat.getColor(resources, R.color.white, null)
                        )
                    )

                    holder.itemView.ivPayment.imageTintList = ColorStateList.valueOf(Color.WHITE)
                } else {
                    holder!!.itemView.llPaymentView.setBackgroundColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )

                    holder.itemView.tvPaymentName.setTextColor(
                        ColorStateList.valueOf(
                            ResourcesCompat.getColor(resources, R.color.black, null)
                        )
                    )

                    holder.itemView.ivPayment.imageTintList = ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.black,
                            null
                        )
                    )
                }
                when (paymentItemList[position].paymentName) {
                    "Wallet" -> {
                        holder.itemView.ivPayment.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_wallet,
                                null
                            )!!
                        )
                        holder.itemView.tvPaymentName.text =
                            resources.getString(R.string.my_wallet) + " (" + Common.getCurrancy(this@PaymentPayActivity) + String.format(
                                Locale.US,
                                "%,.2f",
                                paymentItemList[position].wallet_amount?.toDouble()
                            ) + ")"
                    }
                    "COD" -> {
                        holder.itemView.ivPayment.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_cod,
                                null
                            )!!
                        )
                        holder.itemView.tvPaymentName.text =
                            resources.getString(R.string.cash_payment)
                    }
                    "RazorPay" -> {
                        strRezorPayKey = if (paymentItemList[position].environment == 1) {
                            paymentItemList[position].testPublicKey!!
                        } else {
                            paymentItemList[position].livePublicKey!!
                        }
                        holder.itemView.ivPayment.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_online,
                                null
                            )!!
                        )
                        holder.itemView.tvPaymentName.text =
                            resources.getString(R.string.rezorpay_payment)
                    }
                    "Stripe" -> {

                        holder.itemView.ivPayment.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_stripe,
                                null
                            )!!
                        )
                        holder.itemView.tvPaymentName.text =
                            resources.getString(R.string.sttripe_payment)
                    }
                }


                holder.itemView.setOnClickListener {
                    for (i in 0 until paymentItemList.size) {
                        paymentItemList[i].isSelect = false
                    }
                    paymentItemList[position].isSelect = true
                    strGetData = paymentItemList[position].paymentName!!
                    notifyDataSetChanged()
                }

            }

            override fun setItemLayout(): Int {
                return R.layout.row_payment
            }
        }
        rvPayment.apply {
            adapter = orderHistoryAdapter
            layoutManager = LinearLayoutManager(this@PaymentPayActivity)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = true
        }

    }
}