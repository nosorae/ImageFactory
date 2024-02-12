package com.yessorae.data.remote.firebase.config

import android.view.Display.Mode
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yessorae.common.Logger
import com.yessorae.data.remote.firebase.config.model.Model
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteConfigDataSource @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) {
    private val gson = Gson()
    private var activated = false
    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun activate() = suspendCancellableCoroutine<Boolean> { continuation ->
        if (activated) continuation.resume(true) {}
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            continuation.resume(task.result) {}
        }
    }

    suspend fun getFeaturedModelsOfCheckPoints(): List<Model> {
        activate()

        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_CHECK_POINTS)
        if (json.isEmpty()) return listOf()
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    suspend fun getFeaturedModelsOfLoRa(): List<Model> {
        activate()

        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_LORA)
        if (json.isEmpty()) return listOf()
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    suspend fun getFeaturedModelsOfEmbeddings(): List<Model> {
        activate()

        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_EMBEDDINGS)
        if (json.isEmpty()) return listOf()
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    companion object {
        const val KEY_FEATURED_MODELS_OF_CHECK_POINTS = "featured_models_of_check_points"
        const val KEY_FEATURED_MODELS_OF_LORA = "featured_models_of_embeddings"
        const val KEY_FEATURED_MODELS_OF_EMBEDDINGS = "featured_models_of_lora"
    }
}