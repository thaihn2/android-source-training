package com.transportation.bookcar.data.remote.bean

import android.os.Bundle
import com.google.gson.annotations.SerializedName
import com.transportation.bookcar.data.KEY_LATEST_VERSION
import com.transportation.bookcar.data.KEY_MESSAGE
import com.transportation.bookcar.data.KEY_URL

/**
 * Created on 3/8/2018.
 */
class VersionInfo(
        @SerializedName("message")
        val message: String?,
        @SerializedName("storeUrl")
        val url: String?,
        @SerializedName("latestVersion")
        val latestVersion: String?
) {
    fun toBundle(): Bundle {
        val data = Bundle()
        data.putString(KEY_MESSAGE, message ?: "")
        data.putString(KEY_URL, url ?: "")
        data.putString(KEY_LATEST_VERSION, latestVersion ?: "")
        return data
    }
}
