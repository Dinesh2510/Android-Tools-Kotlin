package com.callberry.callingapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.android.tools.Activities.Contacts.fragment.DialFragment
import com.android.tools.R

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_contact.*

class HomeFragment : Fragment(R.layout.fragment_home_contact) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floatBtnDial.setOnClickListener {
            val dialFragment = DialFragment()
            dialFragment.show(childFragmentManager, "dialog")
        }


    }



    override fun onResume() {
        super.onResume()

        Log.e("recents", "HomeFragment")

    }

}
