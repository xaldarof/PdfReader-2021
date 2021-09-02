package pdf.reader.simplepdfreader.tools

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import pdf.reader.simplepdfreader.presentation.MainActivity
import java.lang.ref.WeakReference

interface PermissionManager {

    fun requestPermission():Boolean
    fun checkPermission(): Boolean

    @RequiresApi(Build.VERSION_CODES.M)
    class Base(private val weakReference: WeakReference<Activity>) : PermissionManager{

        override fun requestPermission(): Boolean {
            return if (checkPermission()) {
               return true
            } else {
                weakReference.get()!!.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 20)
                false
            }
        }

        override fun checkPermission(): Boolean {
            val writePerm =
                ContextCompat.checkSelfPermission(weakReference.get()!!.applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val readPerm =
                ContextCompat.checkSelfPermission(weakReference.get()!!.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
            return writePerm == PackageManager.PERMISSION_GRANTED && readPerm == PackageManager.PERMISSION_GRANTED
        }
    }
}
