package pdf.reader.simplepdfreader.core

data class RequestResult<out T> (val status:Status, val data:T?, val message:String?="Message") {

    fun <T> success(data: T?) : RequestResult<T> {
        return RequestResult(Status.SUCCESS,data,message)
    }

    fun <T> loading(message: String): RequestResult<T> {
        return RequestResult(Status.LOADING,null,message)
    }

    fun <T> error(data: T?,message: String): RequestResult<T> {
        return RequestResult(Status.ERROR,data,message)
    }
}

enum class Status{
    ERROR,
    LOADING,
    SUCCESS
}