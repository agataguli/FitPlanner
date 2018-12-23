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
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import com.who.helathy.fitplanner.helper.validation.UserFormValidationHelper
import kotlinx.android.synthetic.main.new_user_fragment.*
import java.util.Date

class NewUserFragment : Fragment() {
    private var date: Date = DateUtil.stringToDate(1995, 8, 15)
    private var isDateChangeIsInProgress = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                this.saveUser()
                startActivity(Intent(context, MenuActivity::class.java))
            } else {
                Toast.makeText(context, resources.getString(R.string.userFragmentErrorValidationMessage),
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveUser() {
        val user = User(tName = input_name_r.text.toString(), tHeight = input_height_r.text.toString().toInt(),
                tBirthDate = date, tWeight = input_weight_r.text.toString().toInt())
        DatabaseHelper.getInstance(context!!)?.addUserTest(user)
        SharedPreferencesUtil.saveToSharedPreferences(this.requireContext(), StaticKeyValues.KEY_OBJECT_USER, JsonHelper.getJsonFromUser(user))
    }

    private fun isFormValid(): Boolean {
        return UserFormValidationHelper(context!!).
                isFormValid(input_name_r, label_date_r, input_weight_r, input_height_r)
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