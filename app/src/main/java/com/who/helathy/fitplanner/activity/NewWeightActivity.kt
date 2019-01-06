package com.who.helathy.fitplanner.activity

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.domain.Weight
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import com.who.helathy.fitplanner.helper.validation.UserFormValidationHelper
import kotlinx.android.synthetic.main.activity_new_weight.*
import java.util.*

class NewWeightActivity : AppCompatActivity() {

    private var user: User? = null
    private var recentDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_weight)

        user = JsonHelper.getUserFromJson(SharedPreferencesUtil.readFromSharedPreferences(applicationContext, StaticKeyValues.KEY_OBJECT_USER)!!)
        recentDate = DateUtil.stringToDate(SharedPreferencesUtil.readFromSharedPreferences(applicationContext, StaticKeyValues.KEY_WEIGHT_NEWEST_DATE)!!)

        input_prev_weight_r.setText(user!!.weight!!.toString())

        button_save_weight_r.setOnClickListener { _ ->
            if(this.isFormValid()) {
                this.updateWeight()
            } else {
                Toast.makeText(applicationContext, resources.getString(R.string.newWeightErrorValidationMessage),
                        Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun isFormValid(): Boolean {
        return UserFormValidationHelper(applicationContext).isWeightFormValid(input_current_weight_r, input_weight_date_r)
    }

    private fun updateWeight() {
        var beforeMsg = false
        var warningMsg = resources.getString(R.string.confirmSaveNewWeight)
        val date = DateUtil.shortDateFormatStringToDate ((input_weight_date_r.text.toString()))

        if (date.before(recentDate)) {
            beforeMsg = true
            warningMsg = warningMsg + " " + resources.getString(R.string.weightIsBeforeRecentDate)+
                    " " + DateUtil.dateToStringShortDateFormat(recentDate!!)
        }

        AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.newWeight))
                .setMessage(warningMsg)
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    if (!beforeMsg) {
                        this.updateSharedPreferences()
                    }
                    this.insertWeightIntoDatabase(date)
                    startMenuActivity()
                }.setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                    // Do nothing
                }.show()
    }

    private fun startMenuActivity() {
        val intent = Intent(applicationContext, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun insertWeightIntoDatabase(date: Date) {
        val weight = Weight(date = date, value = input_current_weight_r.text.toString().toInt())
        DatabaseHelper.getInstance(applicationContext)?.addWeight(weight)
    }

    private fun updateSharedPreferences() {
        user!!.weight = input_current_weight_r.text.toString().toInt()
        SharedPreferencesUtil.saveToSharedPreferences(applicationContext,
                StaticKeyValues.KEY_OBJECT_USER, JsonHelper.getJsonFromUser(user!!))

    }

}
