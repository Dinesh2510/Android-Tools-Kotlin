package com.android.tools.Activities.Contacts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.tools.Activities.RadioRecyclerview.RadioActivity
import com.android.tools.R
import com.callberry.callingapp.ui.fragment.CallRateFragment
import com.callberry.callingapp.ui.fragment.CreditFragment
import com.callberry.callingapp.ui.fragment.HomeFragment
import com.callberry.callingapp.ui.fragment.MoreFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private var currentPage = 1
    private val homeFragment = HomeFragment()
    private val creditFragment = CreditFragment()
    private val callRateFragment = CallRateFragment()
    private val moreFragment = MoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setupBottomNav()


    }

    override fun onResume() {
        super.onResume()
        homeFragment.onResume()

        Log.e("recents", "MainActivity")
    }

    private fun setupBottomNav() {
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> updateView(1, getString(R.string.app_name), R.drawable.ic_search_main, homeFragment)
                R.id.menu_credits -> updateView(2, getString(R.string.menu_credits), R.drawable.ic_history, creditFragment)
                R.id.menu_call_rates -> updateView(3, getString(R.string.menu_call_rates), -1, callRateFragment)
                R.id.menu_more -> updateView(4, getString(R.string.menu_more), -1, moreFragment)

            }
            true
        }
    }



    private fun init() {

        btnOptionMenu.setOnClickListener {
            if (currentPage == 1)
                route(RadioActivity::class)

            if (currentPage == 2)
                route(RadioActivity::class)
        }
    }
    fun <T : Any> Context.route(activity: KClass<T>) {
        val intent = Intent(this, activity.java)
        startActivity(intent)
    }

    private fun updateView(pageNo: Int, title: String, icon: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, fragment)
            .commit()

        textViewTitle.text = title
        currentPage = pageNo
        if (icon == -1) {
            btnOptionMenu.visibility = View.GONE
        } else {
            btnOptionMenu.visibility = View.VISIBLE
            btnOptionMenu.setImageResource(icon)
        }
    }

    data class Menu(val viewBar: View, val imgViewIcon: ImageView, val menuText: TextView)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

}
