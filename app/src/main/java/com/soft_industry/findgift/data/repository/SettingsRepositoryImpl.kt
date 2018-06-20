package com.soft_industry.findgift.data.repository

import android.app.Activity
import android.content.Context
import com.soft_industry.findgift.domain.repository.SettingsRepository
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

/**
 * Created by user on 4/13/18.
 */
class SettingsRepositoryImpl @Inject constructor(val context: Context) :SettingsRepository{
    val USER_PREFS = "USER_PREFS"
    val USER_LOCALE = "USER_LOCALE"
    override fun loadUserLanguage(): Observable<Locale> {
        return Observable.just(
                context.applicationContext
                .getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE)
                .getString(USER_LOCALE, Locale.getDefault().displayLanguage))
                .map { Locale(it) }

    }
}