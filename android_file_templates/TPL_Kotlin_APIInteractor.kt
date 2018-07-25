package ${PACKAGE_NAME}


import android.content.Context
import com.develapps.baseproject.model.Pagination
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

class ${NAME}(context: Context) : BaseAPIInteractor<User>(context) {

    fun get${ENTITY}s(callback: APIInteractor.APIResponseDataCallback) {
        APIInteractor.get${ENTITY}sAPIInteractor(context).get${ENTITY}s().enqueue(getDefaultDataCallback(callback))
    }

    fun get${ENTITY}s(page: Int, callback: APIInteractor.APIResponseDataCallback) {
        APIInteractor.get${ENTITY}sAPIInteractor(context).get${ENTITY}s(page, Pagination.DEFAULT_PAGE_LIMIT)
                .enqueue(getDefaultDataCallback(callback))
    }

    fun post${ENTITY}(${ENTITY}: ${ENTITY}, callback: APIInteractor.APIResponseDataCallback) {
        APIInteractor.get${ENTITY}sAPIInteractor(context).post${ENTITY}(${ENTITY}).enqueue(getEntityDataCallback(callback))
    }

    fun delete${ENTITY}(${ENTITY}Id: String, callback: APIInteractor.APIResponseSimpleCallback) {
        APIInteractor.get${ENTITY}sAPIInteractor(context).delete${ENTITY}(${ENTITY}Id).enqueue(getDefaultSimpleCallback(callback))
    }

    interface Interface {

        @GET("${ENTITY}s/")
        fun get${ENTITY}s(): Call<ResponseBody>

        @GET("${ENTITY}s/")
        fun get${ENTITY}s(@Query(Pagination.PAGINATION_PAGE_FIELD) page: Int, @Query(Pagination.PAGINATION_LIMIT_FIELD) pageSize: Int = Pagination.DEFAULT_PAGE_LIMIT): Call<ResponseBody>

        @POST("${ENTITY}s/")
        fun post${ENTITY}(@Body user: User): Call<${ENTITY}>

        @DELETE("${ENTITY}s/{${ENTITY}_id}")
        fun delete${ENTITY}(@Path("${ENTITY}_id") ${ENTITY}Id: String): Call<ResponseBody>
    }
}
