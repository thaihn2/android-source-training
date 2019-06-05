package android.thaihn.integratelogin.facebook.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object FacebookUtil {

    private val TAG = FacebookUtil.javaClass.name

    fun getKeyStoreDebug(context: Context) {
        try {
            val info =
                context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")

                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e(TAG, "key hash: $something")
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e(TAG, "name not found: $e1")
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "no such an algorithm: $e")
        } catch (e: Exception) {
            Log.e(TAG, "exception: $e")
        }

    }
}
