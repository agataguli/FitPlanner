package com.who.helathy.fitplanner.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.MenuActivity
import com.who.helathy.fitplanner.activity.NewWeightActivity
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.domain.Weight
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import com.who.helathy.fitplanner.helper.validation.UserFormValidationHelper
import kotlinx.android.synthetic.main.user_fragment.*
import java.util.Date

class UserFragment : Fragment() {
    private var date: Date = DateUtil.stringToDate(1995, 8, 15)
    private var isDateChangeIsInProgress = false
    private var isUserCreated: Boolean? = false
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isUserCreated = SharedPreferencesUtil.readFlagFromSharedPreferences(requireContext(), StaticKeyValues.KEY_FLAG_USER_CREATED);

        super.onViewCreated(view, savedInstanceState)
        if (isUserCreated!!) {
            user = JsonHelper.getUserFromJson(SharedPreferencesUtil.readFromSharedPreferences(requireContext(), StaticKeyValues.KEY_OBJECT_USER)!!)
            date = user!!.birthDate!!
            input_name_r.setText(user!!.name)
            input_birth_date_r.date = date.time
            label_date_r.setText(DateUtil.dateToStringShortDateFormat(date))
            input_weight_r.setText(user!!.weight!!.toString())
            input_weight_r.isEnabled = false
            input_height_r.setText(user!!.height!!.toString())
        } else {
            button_update_weight_r.visibility = View.INVISIBLE
        }
        input_birth_date_r.visibility = View.GONE
        input_birth_date_r.maxDate = Date().time

        this.setOnEventListeners();
    }

    private fun setOnEventListeners() {
        this.setDateInputLostFocusListener()

        label_date_r.setOnClickListener {_ ->
            input_birth_date_r.date = date.time
            label_date_r.error = null
            this.reverseDateFieldsVisibility(isDateLabelVisible = false)
        }

        input_birth_date_r.setOnDateChangeListener { _, year, month, dayOfMonth ->
            this.populateDateInputFields(year, month, dayOfMonth)
        }

        button_save_r.setOnClickListener { _ ->
            if(this.isFormValid()) {
                if (!isUserCreated!!) {
                    this.saveWeight()
                }
                this.saveUser()
                startActivity(Intent(context, MenuActivity::class.java))
            } else {
                Toast.makeText(context, resources.getString(R.string.newEntityErrorValidationMessage),
                        Toast.LENGTH_LONG).show()
            }
        }

        button_update_weight_r.setOnClickListener { _ ->
            startActivity(Intent(context, NewWeightActivity::class.java))
        }
    }

    private fun saveWeight() {
        val weight = Weight(date = Date(), value = input_weight_r.text.toString().toInt())
        DatabaseHelper.getInstance(context!!)?.addWeight(weight)
    }

    private fun saveUser() {
        val user = User(tName = input_name_r.text.toString(), tHeight = input_height_r.text.toString().toInt(),
                tBirthDate = date, tWeight = input_weight_r.text.toString().toInt())
        if (isUserCreated!!) {
            DatabaseHelper.getInstance(context!!)?.updateUser(user)
        } else {
            DatabaseHelper.getInstance(context!!)?.addUser(user)
        }
        SharedPreferencesUtil.saveToSharedPreferences(this.requireContext(), StaticKeyValues.KEY_FLAG_USER_CREATED, true)
        SharedPreferencesUtil.saveToSharedPreferences(this.requireContext(), StaticKeyValues.KEY_WEIGHT_NEWEST_DATE, DateUtil.dateToString(Date()))
        SharedPreferencesUtil.saveToSharedPreferences(this.requireContext(), StaticKeyValues.KEY_OBJECT_USER, JsonHelper.getJsonFromUser(user))
    }

    private fun isFormValid(): Boolean {
        return UserFormValidationHelper(context!!).
                isUserFormValid(input_name_r, label_date_r, input_weight_r, input_height_r)
    }

    private fun populateDateInputFields(year: Int, month: Int, dayOfMonth: Int) {
        date = DateUtil.stringToDate(year, month, dayOfMonth)
        label_date_r.setText(DateUtil.dateToStringShortDateFormat(date))
        this.reverseDateFieldsVisibility(isDateLabelVisible = true)
    }

    private fun reverseDateFieldsVisibility(isDateLabelVisible: Boolean) {
        if(isDateLabelVisible) {
            input_birth_date_r.visibility = View.GONE
            label_date_r.visibility = View.VISIBLE
            isDateChangeIsInProgress = false
        } else {
            input_birth_date_r.visibility = View.VISIBLE
            input_birth_date_r.visibility = View.VISIBLE
            label_date_r.visibility = View.GONE
            isDateChangeIsInProgress = true
        }
    }

    private fun setDateInputLostFocusListener() {
        // CalendarView seems not to respond on focus events,
        // even with requestFocus here: label_date_r.setOnClickListener
        val focusListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus and isDateChangeIsInProgress) {
                if(date == null) {
                    Toast.makeText(context, resources.getString(R.string.birthDateValidationNull),
                            Toast.LENGTH_LONG).show()
                } else {
                    populateDateInputFields(DateUtil.getYear(date), DateUtil.getMonth(date) - 1, DateUtil.getDay(date))
                }
            }
        }

        input_name_r.onFocusChangeListener = focusListener
        input_weight_r.onFocusChangeListener = focusListener
        input_height_r.onFocusChangeListener = focusListener
    }
}