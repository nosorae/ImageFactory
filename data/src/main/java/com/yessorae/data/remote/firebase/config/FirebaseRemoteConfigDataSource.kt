package com.yessorae.data.remote.firebase.config

import android.view.Display.Mode
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yessorae.data.remote.firebase.config.model.Model
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteConfigDataSource @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) {
    fun getFeaturedModelsOfCheckPoints(): List<Model> {
        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_CHECK_POINTS)
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    fun getFeaturedModelsOfLoRa(): List<Model> {
        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_LORA)
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    fun getFeaturedModelsOfEmbeddings(): List<Model> {
        val json = firebaseRemoteConfig.getString(KEY_FEATURED_MODELS_OF_EMBEDDINGS)
        val itemType = object : TypeToken<List<Model>>() {}.type
        return gson.fromJson(json, itemType)
    }

    companion object {
        val gson = Gson()
        val KEY_FEATURED_MODELS_OF_CHECK_POINTS = "featured_models_of_check_points"
        val KEY_FEATURED_MODELS_OF_LORA = "featured_models_of_embeddings"
        val KEY_FEATURED_MODELS_OF_EMBEDDINGS = "featured_models_of_lora"
    }
}