package com.ratnez.myapplication

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ratnez.myapplication.data.ApiService
import com.ratnez.myapplication.data.local.PersonDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    companion object {
        const val TIMEOUT: Long = 45
        val timeUnit = TimeUnit.SECONDS
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun gson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun gsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Singleton
    @Provides
    fun logLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun httpLoggingInterceptor(level: HttpLoggingInterceptor.Level?): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        level?.let { interceptor.setLevel(it) }
        return interceptor
    }

    @Singleton
    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, timeUnit)
            readTimeout(TIMEOUT, timeUnit)
            writeTimeout(TIMEOUT, timeUnit)
            addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun getPersonDB(@ApplicationContext app: Context) = Room.databaseBuilder(app, PersonDB::class.java, "db_person").build()

    @Singleton
    @Provides
    fun getPersonDao(db: PersonDB) = db.personDao()
}