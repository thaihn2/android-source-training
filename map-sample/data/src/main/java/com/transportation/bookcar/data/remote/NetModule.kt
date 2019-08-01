package com.transportation.bookcar.data.remote

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.transportation.bookcar.data.remote.rest.ApiClient

/**
 * Created on 3/7/2018.
 */
@Module
class NetModule(
        val application: Application,
        private var baseUrl: String
) {

    @Provides
    //    @Singleton
    internal fun provideHttpCache(): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    //    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    //    @Singleton
    internal fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = BODY
        val client = OkHttpClient.Builder()
        client.cache(cache)
    //    client.addNetworkInterceptor(httpLoggingInterceptor)
        client.addInterceptor(httpLoggingInterceptor)
        return client.build()
    }

    @Provides
    //    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    //    @Singleton
    internal fun apiRepo(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)
}
