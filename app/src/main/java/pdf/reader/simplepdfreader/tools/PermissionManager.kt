package pdf.reader.simplepdfreader.tools

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

interface PermissionManager {

    fun requestPermission(): Boolean
    fun checkReadPermission(): Boolean
    fun checkWritePermission(): Boolean
    fun checkCameraPermission(): Boolean

    @RequiresApi(Build.VERSION_CODES.M)
    class Base(private val weakReference: WeakReference<Activity>) : PermissionManager {

        override fun requestPermission(): Boolean {
            return if (!checkCameraPermission() || !checkReadPermission() || !checkWritePermission()) {
                weakReference.get()!!.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA), 20)
                true
            } else {
                false
            }
        }

        override fun checkReadPermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                weakReference.get()!!.applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        override fun checkWritePermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                weakReference.get()!!.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        }

        override fun checkCameraPermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                weakReference.get()!!.applicationContext,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }
    }
}
