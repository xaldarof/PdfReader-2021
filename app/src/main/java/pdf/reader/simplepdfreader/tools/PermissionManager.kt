package pdf.reader.simplepdfreader.tools

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import pdf.reader.simplepdfreader.presentation.MainActivity

interface PermissionManager {

    fun requestPermission():Boolean

    @RequiresApi(Build.VERSION_CODES.M)
    class Base(private val activity: MainActivity) : PermissionManager{

        override fun requestPermission(): Boolean {
            return if (checkPermission()) {
                true
            } else {
                activity.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                false
            }
        }

        private fun checkPermission(): Boolean {
            val writePerm =
                ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val readPerm =
                ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
            return writePerm == PackageManager.PERMISSION_GRANTED && readPerm == PackageManager.PERMISSION_GRANTED
        }
    }
}
