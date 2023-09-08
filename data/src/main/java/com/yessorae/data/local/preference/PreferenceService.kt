package com.yessorae.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.google.gson.Gson
import com.yessorae.common.Logger
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequest
import com.yessorae.data.util.DatastoreConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceService @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>
) {
    private val lastTxtToImageRequest = stringPreferencesKey(
        name = DatastoreConstants.KEY_LAST_TXT_TO_IMG_REQUEST
    )

    suspend fun setLastTxtToImageRequest(request: TxtToImgRequest) {
        Logger.data(message = "setLastTxtToImageRequest : ${Gson().toJson(request)}", error = true)
        dataStorePreference.edit { pref ->
            pref[lastTxtToImageRequest] = Gson().toJson(request)
        }
    }

    fun getLastTxtToImageRequest(): Flow<TxtToImgRequest?> {
        return dataStorePreference.data.map { pref ->
            pref[lastTxtToImageRequest]?.let { jsonString ->
                Logger.data(message = "getLastTxtToImageRequest ${Gson().fromJson(jsonString, TxtToImgRequest::class.java)}", error = true)
                Gson().fromJson(jsonString, TxtToImgRequest::class.java)
            }
        }
    }
}