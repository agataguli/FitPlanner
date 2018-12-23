package com.who.helathy.fitplanner.helper.validation

import android.content.Context
import android.widget.EditText
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.util.DateUtil
import java.lang.Exception
import java.util.Date
import java.util.logging.Logger
import java.util.regex.Pattern

class UserFormValidationHelper(context: Context) {
    private val LOGGER = Logger.getLogger(UserFormValidationHelper::class.java.name)
    private val helperContext = context

    fun isFormValid(nameInput: EditText, dateInput: EditText, weightInput: EditText,
                           heightInput: EditText): Boolean {
        return validateName(nameInput) and validateBirthDate(dateInput) and
                validateHeight(heightInput) and validateWeight(weightInput)
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
        if(!isWeightOrHeightValid(input.text.toString())) {
            input.error = helperContext.resources.getString(R.string.heightValidationError)
            return false
        }
        return true
    }

    private fun validateWeight(input: EditText): Boolean {
        if(!isWeightOrHeightValid(input.text.toString())) {
            input.error = helperContext.resources.getString(R.string.weightValidationError)
            return false
        }
        return true
    }

    // TODO: agataguli - check why Kotlin wants this isEmpty condition in a separated line
    // It looks like even if 1st condition is false return is still checking 2nd, 3nd... etc condition boolean value
    private fun isValidName(s: String): Boolean {
        if(s.isEmpty()) return false
        return Character.isUpperCase(s[0]) and (s.length > 2) and (s.length < 16)
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

    private fun isWeightOrHeightValid(s: String): Boolean {
        if(!isIntValue(s)) return false
        return s.toInt() > 0
    }

    private fun isIntValue(s: String): Boolean {
        val numericRegex = "[0-9]+"
        val pattern : Pattern = Pattern.compile(numericRegex)
        return pattern.matcher(s).matches()
    }


}