package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.preference.PreferenceService
import javax.inject.Inject

class InitialPromptRepository @Inject constructor(
    private val promptDao: PromptDao,
    private val preferenceService: PreferenceService
) {
}