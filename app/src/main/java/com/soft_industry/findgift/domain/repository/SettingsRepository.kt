package com.soft_industry.findgift.domain.repository

import io.reactivex.Observable
import java.util.*

/**
 * Created by user on 3/23/18.
 */
interface SettingsRepository {
    fun loadUserLanguage(): Observable<Locale>
}