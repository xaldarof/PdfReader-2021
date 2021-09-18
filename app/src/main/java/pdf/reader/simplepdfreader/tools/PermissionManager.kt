package pdf.reader.simplepdfreader.tools

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import pdf.reader.simplepdfreader.BuildConfig
import pdf.reader.simplepdfreader.presentation.MainActivity
import java.lang.ref.WeakReference
import androidx.core.content.ContextCompat.startActivity
import pdf.reader.simplepdfreader.presentation.dialogs.RequestPermissionDialog


interface PermissionManager {

    fun requestPermission(): Boolean
    fun checkReadPermission(): Boolean
    fun checkWritePermission(): Boolean
    fun checkCameraPermission(): Boolean
    fun checkManagePermission(): Boolean


    @RequiresApi(Build.VERSION_CODES.M)
    class Base(private val weakReference: WeakReference<Activity>,private val context: Context) : PermissionManager {

        override fun requestPermission(): Boolean {
            return if (!checkCameraPermission() && !checkReadPermission() && !checkWritePermission() && !checkManagePermission()) {
                if (Build.VERSION.SDK_INT >= 30) {
                    weakReference.get()!!.requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.CAMERA), 20)

                    if (!Environment.isExternalStorageManager()) {
                        RequestPermissionDialog.Base(context).showDialog()
                    }
                } else {
                    weakReference.get()!!.requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.CAMERA), 20)
                }
                false
            } else {
                true
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

        override fun checkManagePermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                weakReference.get()!!.applicationContext,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

    }
}
