package com.example.videofeed.di

import android.content.Context
import com.example.videofeed.api.ApiService
import com.example.videofeed.dao.NoteDao
import com.example.videofeed.dao.NoteDatabase
import com.example.videofeed.dao.NoteRepository
import com.example.videofeed.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient().build())
            .build()
            .create(ApiService::class.java)


    private fun getOkHttpClient(): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.readTimeout(60, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(interceptor)
        client.addInterceptor(Interceptor { chain ->
            val request = chain.request()
            chain.proceed(request)
        })

        client.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }

        return client
    }


    @Singleton
    @Provides
    fun provideRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return NoteDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.datarecordDao()
    }
}