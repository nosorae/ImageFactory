package com.yessorae.common

import timber.log.Timber

object Logger {
    private const val FULL_TAG = "SRN_FULL_TAG"
    private const val DATA_TAG = "SRN_DATA_TAG"
    private const val NETWORK_TAG = "NETWORK_TAG"
    private const val PRESENTATION_TAG = "PRESENTATION_TAG"
    private const val UI_STATE_TAG = "UI_STATE_TAG"
    private const val TEMP_TAG = "TEMP_TAG"

    fun initialize() {
        Timber.plant(Timber.DebugTree())
    }

    fun data(message: String, error: Boolean = false) {
        full(message, error)
        log(DATA_TAG, message, error)
    }

    fun presentation(message: String, error: Boolean = false) {
        full(message, error)
        log(PRESENTATION_TAG, message, error)
    }

    fun network(message: String, error: Boolean = false) {
        data(message, error)
        log(NETWORK_TAG, message, error)
    }

    fun uiState(message: String, error: Boolean = false) {
        presentation(message, error)
        log(UI_STATE_TAG, message, error)
    }

    fun temp(message: String, error: Boolean = false) {
        full(message, error)
        log(TEMP_TAG, message, error)
    }

    fun recordException(message: String) {
        // TODO Crashlytics, Analytics
    }

    fun recordException(error: Throwable) {
        // TODO Crashlytics, Analytics
    }

    private fun full(message: String, error: Boolean = false) {
        log(FULL_TAG, message, error)
    }

    private fun log(tag: String, message: String, error: Boolean = false) {
        Timber.tag(tag).also {
            if (error) {
                it.e(message)
            } else {
                it.d(message)
            }
        }
    }
}
