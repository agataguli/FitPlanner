package com.who.helathy.fitplanner.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.fragment.UserFragment

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setFragmentView()
    }

    private fun setFragmentView() {
        val fragmentT: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentT.replace(R.id.profile_fragment_to_put, UserFragment())
        fragmentT.commit()
    }
}
