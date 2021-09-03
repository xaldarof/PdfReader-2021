package pdf.reader.simplepdfreader.tools

import android.text.Editable
import android.text.TextWatcher


interface TextWatcherWrapper{
    fun onTextChange(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
}

class TextWatcher(private val textWatcherWrapper: TextWatcherWrapper) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textWatcherWrapper.onTextChange(p0,p1,p2,p3)
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}