package com.android.tools.Activities.SingleSelection

import com.android.tools.Activities.Home_A
import com.android.tools.R
import com.android.tools.base.BaseActivity


import kotlinx.android.synthetic.main.activity_successpayment.*

class PaymentsuccessfulActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_successpayment

    override fun InitView() {
        tvProceed.setOnClickListener {
            openActivity(Home_A::class.java)
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        return
    }
}