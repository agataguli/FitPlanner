package com.who.helathy.fitplanner.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.*
import kotlinx.android.synthetic.main.hello_user_fragment.*

class HelloUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hello_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventListeners()
    }

    private fun setEventListeners() {
        profile_button_r.setOnClickListener {_ ->
            activity?.startActivity(Intent(context, ProfileActivity::class.java))
        }

        sport_button_r.setOnClickListener { _ ->
            activity?.startActivity(Intent(context, SportListActivity::class.java))
        }

        settings_button_r.setOnClickListener { _ ->
            activity?.startActivity(Intent(context, SettingsActivity::class.java))
        }

        scores_button_r.setOnClickListener { _ ->
            activity?.startActivity(Intent(context, ScoresActivity::class.java))
        }

        my_trainings_button_r.setOnClickListener { _ ->
            activity?.startActivity(Intent(context, MyTrainingsActivity::class.java))
        }

        train_button_r.setOnClickListener { _ ->
            activity?.startActivity(Intent(context, TrainActivity::class.java))
        }
    }

}
