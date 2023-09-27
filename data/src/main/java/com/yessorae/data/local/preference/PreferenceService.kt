package com.yessorae.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.yessorae.common.Logger
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.util.DatastoreConstants
import com.yessorae.data.util.LocalDateTimeConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceService @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>,
    private val localDateTimeConverter: LocalDateTimeConverter
) {
    private val lastTxtToImageRequest = stringPreferencesKey(
        name = DatastoreConstants.KEY_LAST_TXT_TO_IMG_REQUEST
    )

    private val lastModelUpdateTime = stringPreferencesKey(
        name = DatastoreConstants.KEY_LAST_MODEL_UPDATE_TIME
    )

    private val completeInitPrompt = booleanPreferencesKey(
        name = DatastoreConstants.KEY_COMPLETE_INIT_PROMPT
    )

    suspend fun setLastTxtToImageRequest(request: TxtToImgRequestBody) {
        Logger.data(message = "setLastTxtToImageRequest : ${Gson().toJson(request)}", error = true)
        dataStorePreference.edit { pref ->
            pref[lastTxtToImageRequest] = Gson().toJson(request)
        }
    }

    fun getLastTxtToImageRequest(): Flow<TxtToImgRequestBody?> {
        return dataStorePreference.data.map { pref ->
            pref[lastTxtToImageRequest]?.let { jsonString ->
                Logger.data(message = "getLastTxtToImageRequest ${Gson().fromJson(jsonString, TxtToImgRequestBody::class.java)}", error = true)
                Gson().fromJson(jsonString, TxtToImgRequestBody::class.java)
            }
        }
    }

    suspend fun setLastModelUpdateTime() {
        Logger.data("setLastModelUpdateTime now : ${localDateTimeConverter.fromLocalDateTime(LocalDateTime.now())}")
        dataStorePreference.edit { pref ->
            pref[lastModelUpdateTime] = localDateTimeConverter.fromLocalDateTime(LocalDateTime.now())
        }
    }

    suspend fun getLastModelUpdateTime(): LocalDateTime? {
        return dataStorePreference.data.map { pref ->
            pref[lastModelUpdateTime]?.let { lastTime ->
                localDateTimeConverter.toLocalDateTime(lastTime)
            }
        }.firstOrNull()
    }

    suspend fun setCompleteInitPromptData() {
        dataStorePreference.edit { pref ->
            pref[completeInitPrompt] = true
        }
    }

    suspend fun getCompleteInitPromptData(): Boolean {
        return dataStorePreference.data.map { pref ->
            pref[completeInitPrompt]
        }.firstOrNull() ?: false
    }
}
