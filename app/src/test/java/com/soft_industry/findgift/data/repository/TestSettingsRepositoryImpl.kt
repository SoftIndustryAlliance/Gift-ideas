package com.soft_industry.findgift.data.repository

import com.soft_industry.findgift.domain.repository.SettingsRepository
import io.reactivex.subjects.ReplaySubject
import java.util.*

class TestSettingsRepositoryImpl: SettingsRepository {
    val relay = ReplaySubject.create<Locale>()
    override fun loadUserLanguage() = relay
}