package com.who.helathy.fitplanner.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.fragment.HelloUserFragment
import com.who.helathy.fitplanner.activity.fragment.UserFragment
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil
import com.who.helathy.fitplanner.helper.util.StaticKeyValues

class MenuActivity : AppCompatActivity() {
    private var isUserCreated: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        this.isUserCreated = SharedPreferencesUtil.readFlagFromSharedPreferences(this, StaticKeyValues.KEY_FLAG_USER_CREATED)

        setFragmentView()
    }

    private fun setFragmentView() {
        var fragmentToSet: Fragment
        if(this.isUserCreated!!) {
            fragmentToSet = HelloUserFragment()
        } else {
            fragmentToSet = UserFragment()
        }

        val fragmentT: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentT.replace(R.id.menu_fragment_to_put, fragmentToSet)
        fragmentT.commit()
    }
}