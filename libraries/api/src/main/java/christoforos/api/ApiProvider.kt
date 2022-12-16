package christoforos.api

import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import timber.log.Timber
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class ApiProvider {

    companion object {
        private const val BASE_URL = "https://618d3aa7fe09aa001744060a.mockapi.io/api/"
        private const val TIMEOUT = 5L
        private const val EXCEPTION_ERROR_CODE = 900
        private const val TIMEOUT_EXCEPTION_ERROR_CODE = 901
        private const val EXCEPTION_ERROR_MSG =
            "Api Request Exception ($EXCEPTION_ERROR_CODE)"
        private const val TIMEOUT_EXCEPTION_ERROR_MSG =
            "Socket Timeout Exception ($TIMEOUT_EXCEPTION_ERROR_CODE)"
        private const val LOG_TAG = "API"
    }

    fun getApi(): SportsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getHttpClient())
        .build()
        .create()

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getErrorInterceptor())
            .addInterceptor(getLoggingInterceptor())
        return builder.build()
    }

    private fun getErrorInterceptor() = Interceptor { chain ->
        val request: Request = chain.request()

        try {
            chain.proceed(request)
        } catch (e: Exception) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(EXCEPTION_ERROR_CODE.takeUnless { e is SocketTimeoutException }
                    ?: run { TIMEOUT_EXCEPTION_ERROR_CODE })
                .message(EXCEPTION_ERROR_MSG.takeUnless { e is SocketTimeoutException }
                    ?: run { TIMEOUT_EXCEPTION_ERROR_MSG })
                .body(e.toString().toResponseBody(null))
                .build()
        }
    }

    private fun getLoggingInterceptor() = HttpLoggingInterceptor {
        Timber.tag(LOG_TAG).d(it)
    }.apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

}