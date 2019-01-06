package com.who.helathy.fitplanner.helper.validation

import android.content.Context
import android.widget.EditText
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isPositiveInt
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isValidName
import com.who.helathy.fitplanner.helper.validation.ValidationUtil.Companion.isValidUrl
import java.util.logging.Logger

class SportFormValidationHelper(context: Context) {
    private val LOGGER = Logger.getLogger(SportFormValidationHelper::class.java.name)
    private val helperContext = context

    fun isSportFormValid(nameInput: EditText, kcalInput: EditText, urlInput: EditText): Boolean {
        return validateName(nameInput) and validateKcal(kcalInput) and validateUrl(urlInput)
    }

    private fun validateUrl(urlInput: EditText): Boolean {
        if (!isValidUrl(urlInput.text.toString())) {
            urlInput.error = helperContext.resources.getString(R.string.sportUrlValidationError)
            return false
        }
        return true
    }

    private fun validateKcal(kcalInput: EditText): Boolean {
        if (!isPositiveInt(kcalInput.text.toString())) {
            kcalInput.error = helperContext.resources.getString(R.string.kcalPerHValidationError)
            return false
        }
        return true
    }

    private fun validateName(nameInput: EditText): Boolean {
        if (!isValidName(nameInput.text.toString())) {
            nameInput.error = helperContext.resources.getString(R.string.nameValidationError)
            return false
        }
        return true
    }

}
