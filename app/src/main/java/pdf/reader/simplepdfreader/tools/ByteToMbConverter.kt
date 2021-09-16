package pdf.reader.simplepdfreader.tools

import java.text.DecimalFormat

interface ByteToMbConverter {

    fun convert(byte: Long): String

    class Base : ByteToMbConverter {
        override fun convert(byte: Long): String {
            val df = DecimalFormat("0.00")
            val sizeKb = 1024.0f
            val sizeMb = sizeKb * sizeKb
            val sizeGb = sizeMb * sizeKb
            val sizeTerra = sizeGb * sizeKb
            return when {
                byte < sizeMb -> df.format(byte / sizeKb)
                    .toString() + " КБ"
                byte < sizeGb -> df.format(byte / sizeMb)
                    .toString() + " МБ"
                byte < sizeTerra -> df.format(byte / sizeGb)
                    .toString() + " ГБ"
                else -> ""
            }
        }
    }
}
