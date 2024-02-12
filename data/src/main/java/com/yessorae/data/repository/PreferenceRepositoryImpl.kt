package com.yessorae.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yessorae.common.Logger
import com.yessorae.data.util.DatastoreConstants
import com.yessorae.data.util.LocalDateTimeConverter
import com.yessorae.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>,
    private val localDateTimeConverter: LocalDateTimeConverter
) : PreferenceRepository {
    private val lastTxtToImageRequestId = longPreferencesKey(
        name = DatastoreConstants.KEY_LAST_TXT_TO_IMG_REQUEST_ID
    )

    private val lastModelUpdateTime = stringPreferencesKey(
        name = DatastoreConstants.KEY_LAST_MODEL_UPDATE_TIME
    )

    private val completeInitPrompt = booleanPreferencesKey(
        name = DatastoreConstants.KEY_COMPLETE_INIT_PROMPT
    )

    override suspend fun setLastTxtToImageRequestHistoryId(historyId: Long) {
        dataStorePreference.edit { pref ->
            pref[lastTxtToImageRequestId] = historyId
        }
    }

    override suspend fun getLastTxtToImageRequestHistoryId(): Long? {
        return dataStorePreference.data.map { pref ->
            pref[lastTxtToImageRequestId]
        }.firstOrNull()
    }

    override suspend fun setLastModelUpdateTime() {
        Logger.data(
            "setLastModelUpdateTime now : ${
            localDateTimeConverter.fromLocalDateTime(
                LocalDateTime.now()
            )
            }"
        )
        dataStorePreference.edit { pref ->
            pref[lastModelUpdateTime] =
                localDateTimeConverter.fromLocalDateTime(LocalDateTime.now())
        }
    }

    override suspend fun getLastModelUpdateTime(): LocalDateTime? {
        return dataStorePreference.data.map { pref ->
            pref[lastModelUpdateTime]?.let { lastTime ->
                localDateTimeConverter.toLocalDateTime(lastTime)
            }
        }.firstOrNull()
    }

    override suspend fun setCompleteInitPromptData() {
        dataStorePreference.edit { pref ->
            pref[completeInitPrompt] = true
        }
    }

    override suspend fun getCompleteInitPromptData(): Boolean {
        return dataStorePreference.data.map { pref ->
            pref[completeInitPrompt]
        }.firstOrNull() ?: false
    }
}
