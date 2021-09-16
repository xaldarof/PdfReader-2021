package pdf.reader.simplepdfreader.core

data class Resource<out T> (val status:Status,val data:T?,val message:String?) {

    fun <T> success(data: T?) : Resource<T>{
        return Resource(Status.SUCCESS,data,null)
    }
    fun<T> loading(data: T?,message: String):Resource<T>{
        return Resource(Status.LOADING,data,message)
    }
    fun <T> error(data: T?,message: String):Resource<T>{
        return Resource(Status.ERROR,data,message)
    }
}

enum class Status{
    ERROR,
    LOADING,
    SUCCESS
}