package com.who.helathy.fitplanner.helper.validation

import android.content.Context
import android.widget.EditText
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isPositiveInt
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isValidName
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isValidUrl
import java.util.*
import java.util.logging.Logger

class TrainingValidationHelper(context: Context) {

    fun isSportNotificationValid(date: Date): Boolean {
        return validateTrainingDate(date)
    }

    private fun validateTrainingDate(date: Date): Boolean {
        return date.after(Date())
    }

}
