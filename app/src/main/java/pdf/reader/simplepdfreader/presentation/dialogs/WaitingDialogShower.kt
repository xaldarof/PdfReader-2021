package pdf.reader.simplepdfreader.presentation.dialogs

import android.app.Dialog
import android.content.Context
import com.github.ybq.android.spinkit.style.Wave
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.databinding.WaitingLayoutBinding

interface WaitingDialogShower {

    class Base(context: Context) : Dialog(context, R.style.CustomDialogWaiting) {
        override fun show() {
            super.show()
            val binding = WaitingLayoutBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setCancelable(false)
            binding.waiting.setIndeterminateDrawable(Wave())
        }
    }
}