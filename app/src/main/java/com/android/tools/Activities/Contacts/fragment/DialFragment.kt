package com.android.tools.Activities.Contacts.fragment

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.tools.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_dial.*

class DialFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
    }






    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val bottomSheet =
            dialog!!.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val view = view
        view!!.post {
            val parent = view.parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view.measuredHeight
        }

    }

    private fun enterPhoneNo(input: String) {
        if (textViewPhoneNo.text.isEmpty()) {
            textViewPhoneNo.text = input
        } else {
            val currentNo: String = textViewPhoneNo.text.toString()
            textViewPhoneNo.text = currentNo + input
        }

        onPhoneNumberEnter()
    }

    private fun onPhoneNumberEnter() {

        if (textViewDialCode.text.length <= 1 || textViewPhoneNo.text.isEmpty()) {
            return
        }

        val dialCode = textViewDialCode.text.toString()
        val phoneNo = dialCode + textViewPhoneNo.text.toString()

    }

    private fun backSpace() {
        if (textViewPhoneNo.text.toString().isNotEmpty()) {
            var phoneNo: String = textViewPhoneNo.text.toString()
            phoneNo = phoneNo.substring(0, phoneNo.length - 1)
            textViewPhoneNo.text = phoneNo
        }

        onPhoneNumberEnter()
    }

    private fun initViews() {
        dial_0.setOnClickListener { enterPhoneNo("0") }
        dial_1.setOnClickListener { enterPhoneNo("1") }
        dial_2.setOnClickListener { enterPhoneNo("2") }
        dial_3.setOnClickListener { enterPhoneNo("3") }
        dial_4.setOnClickListener { enterPhoneNo("4") }
        dial_5.setOnClickListener { enterPhoneNo("5") }
        dial_6.setOnClickListener { enterPhoneNo("6") }
        dial_7.setOnClickListener { enterPhoneNo("7") }
        dial_8.setOnClickListener { enterPhoneNo("8") }
        dial_9.setOnClickListener { enterPhoneNo("9") }
        btnBack.setOnClickListener { dismiss() }
        btnBackspace.setOnClickListener { backSpace() }

        btnClear.setOnClickListener {
            textViewPhoneNo.text = ""
            onPhoneNumberEnter()
        }
    }


}
