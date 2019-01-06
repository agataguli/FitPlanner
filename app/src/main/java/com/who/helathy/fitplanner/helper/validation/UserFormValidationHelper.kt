package com.who.helathy.fitplanner.helper.validation

import android.content.Context
import android.widget.EditText
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isPositiveInt
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isShortDateFormatted
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isValidName
import java.lang.Exception
import java.util.Date
import java.util.logging.Logger

class UserFormValidationHelper(context: Context) {
    private val LOGGER = Logger.getLogger(UserFormValidationHelper::class.java.name)
    private val helperContext = context

    fun isUserFormValid(nameInput: EditText, dateInput: EditText, weightInput: EditText,
                        heightInput: EditText): Boolean {
        return validateName(nameInput) and validateBirthDate(dateInput) and
                validateHeight(heightInput) and validateWeight(weightInput)
    }

    fun isWeightFormValid(weightInput: EditText, dateInput: EditText): Boolean {
        return validateWeight(weightInput) and validateWeightDate(dateInput)
    }

    private fun validateName(input: EditText): Boolean {
        if(!isValidName(input.text.toString())) {
            input.error = helperContext.resources.getString(R.string.nameValidationError)
            return false
        }
        return true
    }

    private fun validateBirthDate(input: EditText): Boolean {
        val birthDateString: String = input.text.toString()
        if(!isValidBirthDate(birthDateString)) {
            input.error = helperContext.resources.getString(R.string.birthDateValidationError)
            return false
        }
        return true
    }

    private fun validateHeight(input: EditText): Boolean {
        if(!isPositiveInt(input.text.toString())) {
            input.error = helperContext.resources.getString(R.string.heightValidationError)
            return false
        }
        return true
    }

    private fun validateWeight(input: EditText): Boolean {
        if(!isPositiveInt(input.text.toString())) {
            input.error = helperContext.resources.getString(R.string.weightValidationError)
            return false
        }
        return true
    }

    private fun validateWeightDate(inputDate: EditText): Boolean {
        if(!isShortDateFormatted(inputDate.text.toString())) {
            inputDate.error = helperContext.resources.getString(R.string.weightDateValidationError)
            return false
        }
        return true
    }
    
    private fun isValidBirthDate(dateString: String): Boolean {
        if(dateString.isEmpty()) return false

        val minBirthDate = DateUtil.stringToDate(1900,0,0)
        val maxBirthDate = Date()
        return try {
            val d = DateUtil.shortDateFormatStringToDate(dateString)
            d.after(minBirthDate) and d.before(maxBirthDate)
        } catch (e: Exception) {
            LOGGER.warning("Exception during date parsing/validation thrown: ${e.message}")
            false
        }
    }

}
