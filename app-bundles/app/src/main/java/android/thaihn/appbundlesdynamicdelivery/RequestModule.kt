package android.thaihn.appbundlesdynamicdelivery

import android.content.Context
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus


class RequestModule {

    companion object {
        private val TAG = RequestModule::class.java.simpleName
    }

    fun requestModule(context: Context) {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        val request = SplitInstallRequest.newBuilder()
            .addModule("dynamic_feature")
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener { sessionId ->
            }
            .addOnFailureListener { exception ->

            }
    }

    fun requestWithCallback(context: Context) {
        var mySessionId = 0

        val splitInstallManager = SplitInstallManagerFactory.create(context)

        val request = SplitInstallRequest
            .newBuilder()
            .addModule("dynamic-module")
            .build()

        val listener =
            SplitInstallStateUpdatedListener { splitInstallSessionState ->
                if (splitInstallSessionState.sessionId() == mySessionId) {
                    when (splitInstallSessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            Log.d(TAG, "Dynamic Module downloaded")
                        }
                    }
                }
            }

        splitInstallManager.registerListener(listener)

        splitInstallManager.startInstall(request)
            .addOnFailureListener { e -> Log.d(TAG, "Exception: $e") }
            .addOnSuccessListener { sessionId -> mySessionId = sessionId }
    }

    fun getModuleInstalled(context: Context) {
        val installedModule = SplitInstallManagerFactory.create(context).installedModules
    }
}
