package ru.rsue.borisov.csqandroidclient

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkService {

    private val BASE_URL: String = "http://192.168.0.105:8080/csq-api"

    fun getApolloClient(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .build()

        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }

    fun getApolloClientWithTokenInterceptor(token: String, auth: String): ApolloClient {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()

                val builder: Request.Builder = original
                    .newBuilder()
                    .method(original.method, original.body)

                builder
                    .header("Authorization", "Bearer $token")
                    .addHeader("Authorized", auth)
                return@Interceptor chain.proceed(builder.build())
            })
            .build()

        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(httpClient)
            .build()
    }


    companion object {
        private var mInstance: NetworkService? = null

        fun getInstance(): NetworkService? {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }
    }
}