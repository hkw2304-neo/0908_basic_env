package com.hkw.a0908_test.util

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.hkw.a0908_test.data.service.TestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiUtil {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            when {
                message.startsWith("--> GET") || message.startsWith("--> POST") -> {
                    try {
                        val method = if (message.startsWith("--> GET")) "GET" else "POST"
                        val fullUrl = message.substringAfter("--> $method ").trim()
                        if (fullUrl.contains("?")) {
                            val baseUrl = fullUrl.substringBefore("?")
                            val queryString = fullUrl.substringAfter("?")
                            val params =
                                queryString.split("&").joinToString("\n    ") {
                                    java.net.URLDecoder.decode(it, "UTF-8")
                                }
                           Log.d(
                                "OKHTTP_API",
                                "🚀 [METHOD] : $method\n📍 [URL]    : $baseUrl\n📝 [PARAMS] :\n    $params"
                            )
                        } else {
                           Log.d("OKHTTP_API", message)
                        }
                    } catch (e: Exception) {
                       Log.d("OKHTTP_API", e.toString())
                    }
                }

                message.contains(":") && !message.startsWith("{") && !message.startsWith("[") -> {
                   Log.d("OKHTTP_API", "🔑 [HEADER] : $message")
                }

                message.startsWith("<-- 200") || message.startsWith("<-- HTTP") -> {
                    Log.d("OKHTTP_API", "✅ [RESPONSE STATUS] : $message")
                }

                message.startsWith("{") || message.startsWith("[") -> {
                    try {
                        val prettyJson = GsonBuilder().setPrettyPrinting().create()
                            .toJson(JsonParser.parseString(message))
                        Log.d("OKHTTP_API", "📦 [BODY] :\n$prettyJson")
                    } catch (e: Exception) {
                        Log.d("OKHTTP_API", message)
                    }
                }
            }
        }.apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideTestRetrofit(loggingInterceptor: HttpLoggingInterceptor): Retrofit {
        val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val newRequest = originalRequest.newBuilder()
                        .header("Content-Type", "application/json") // 예시: Content-Type
                        .header("Authorization", "Bearer YOUR_TOKEN") // 예시: 토큰
                        .header("Custom-Header", "MyValue") // Custom Header
                        .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(loggingInterceptor)
                .build()
        return Retrofit
            .Builder()
            .baseUrl("https://www.naver.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTestApiService(
        retrofit: Retrofit,
    ): TestService = retrofit.create(TestService::class.java)


}