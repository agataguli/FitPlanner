package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.ListView
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.adapter.SportAdapter
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import kotlinx.android.synthetic.main.activity_sport_list.*

class SportListActivity : AppCompatActivity() {
    private var listViewAdapter: SportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_list)

        val sportList = DatabaseHelper.getInstance(applicationContext)?.getAllSports()
        listViewAdapter = SportAdapter(this, sportList!!)
        lv_sports!!.adapter = listViewAdapter
        lv_sports!!.invalidateViews()

        this.setEventListeners()
    }

    private fun setEventListeners() {
        lv_sports.setOnItemClickListener { parent, view, position, id ->
            val entry: Sport = parent.adapter.getItem(position) as Sport
            val intent = Intent(this, SportDetailsActivity::class.java)
            intent.putExtra(StaticKeyValues.KEY_INTENT_SPORT_JSON, JsonHelper.getJsonFromSport(sport = entry))
            startActivity(intent)
        }

        button_add_sport.setOnClickListener { _ ->
            startActivity(Intent(this, NewSportActivity::class.java))
        }
    }
}
