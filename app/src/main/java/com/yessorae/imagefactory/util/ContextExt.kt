package com.yessorae.imagefactory.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.yessorae.common.Logger
import java.util.Locale

@Composable
fun getActivity() = LocalContext.current as ComponentActivity

fun Context.showToast(stringModel: StringModel, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringModel.get(this), duration).show()
}

fun Context.redirectToWebBrowser(link: String, onActivityNotFoundException: () -> Unit) {
    Intent(Intent.ACTION_VIEW, Uri.parse(link)).also { intent ->
        try {
            this.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            onActivityNotFoundException()
            Logger.recordException(e)
        } catch (e: Exception) {
            Logger.recordException(e)
        }
    }
}

fun Context.getSettingsLocale(): Locale {
    val config = android.content.res.Configuration()
    val default = Locale.getDefault()
    Settings.System.getConfiguration(this.contentResolver, config)
    return try {
        if (config.locales.size() == 0) {
            Locale.getDefault()
        } else {
            config.locales.get(0)
        }
    } catch (e: NullPointerException) {
        default
    } catch (e: Exception) {
        Logger.recordException(error = e)
        default
    }
}
