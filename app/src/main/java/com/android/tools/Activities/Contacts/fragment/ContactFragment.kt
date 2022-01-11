package com.callberry.callingapp.ui.fragment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.tools.R
import com.android.tools.utils.MaterialProgressDialog

import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : Fragment(R.layout.fragment_contact) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }

    private fun getContacts() {

    }


}
