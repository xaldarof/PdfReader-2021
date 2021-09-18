package pdf.reader.simplepdfreader.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.net.Uri
import android.provider.Settings
import android.view.Window
import pdf.reader.simplepdfreader.BuildConfig
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.databinding.PermissionLayoutBinding


interface RequestPermissionDialog {

    fun showDialog()

    fun registerCalLBack(callBack: Base.PermissionCallBack)

    class Base(private val context: Context) : RequestPermissionDialog {

        override fun showDialog() {
            val dialog = Dialog(context,R.style.BottomSheetDialogTheme)
            val binding = PermissionLayoutBinding.inflate(dialog.layoutInflater)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(binding.root)

            binding.allowBtn.setOnClickListener {
                val uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
                context.startActivity(intent)
                dialog.dismiss()
            }
            binding.denyBtn.setOnClickListener {
                callBack?.request(false)
            }
            dialog.show()
        }


        private var callBack:PermissionCallBack? = null
        interface PermissionCallBack {
            fun request(result:Boolean)
        }

        override fun registerCalLBack(callBack: PermissionCallBack) {
            this.callBack = callBack
        }

    }
}