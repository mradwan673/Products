package com.radwan.products.presentation.common.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.provider.Settings.Secure
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.LocaleManagerCompat.getApplicationLocales
import com.radwan.products.presentation.common.enums.Language


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("HardwareIds")
fun Context.getAndroidDeviceId(): String = Secure.getString(contentResolver, Secure.ANDROID_ID)


fun Context.dpToPx(dp: Int): Int =
    (dp * resources.displayMetrics.density).toInt()


fun Context.getCurrentLocale(): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        var language: String? =
            getSystemService(LocaleManager::class.java).applicationLocales.get(0)?.language
        if (language == null) {
            language = resources.configuration.locales.get(0).language
        }
        language ?: Language.ENGLISH.value
    } else {
        var language = getApplicationLocales(this).get(0)?.language
        if (language == null) {
            language = resources.configuration.locales.get(0).language
        }
        language ?: Language.ENGLISH.value
    }

fun Context.setCurrentLocale(newLanguage: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(newLanguage)
    }
}


