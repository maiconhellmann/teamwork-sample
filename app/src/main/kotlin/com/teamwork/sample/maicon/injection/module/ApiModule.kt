package com.teamwork.sample.maicon.injection.module

import com.google.gson.*
import com.teamwork.sample.maicon.BuildConfig
import com.teamwork.sample.maicon.data.remote.interceptor.RequestInterceptor
import com.teamwork.sample.maicon.data.remote.service.SampleService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {

    /**
     * Prove o parser de Json para a aplicação
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()

        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, _, _ ->
            json?.asJsonPrimitive?.asString.let {
                return@JsonDeserializer sdf.parse(it)
            }
        })

        builder.registerTypeAdapter(Date::class.java, JsonSerializer<Date> { date, _, _ ->
            JsonPrimitive(sdf.format(date))
        })

        return builder.create()
    }


    /**
     * Prove o interceptor das requisições. Utilizado para adicionar header de token, por exemplo.
     */
    @Provides
    @Singleton
    fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }
    /**
     * Provê o interceptor de logging das requisições
     */
    @Provides
    @Singleton
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        //Adiciona log às requisições
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return logInterceptor
    }

    /**
     * Provê o httpClient padrão para o App
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor,
                            logInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val builder = OkHttpClient.Builder()
        //add interceptors
        builder.addInterceptor(logInterceptor)
        builder.addInterceptor(requestInterceptor)

        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.readTimeout(1, TimeUnit.MINUTES)
        builder.readTimeout(1, TimeUnit.MINUTES)

        return builder.build()
    }

    /**
     * Provê o PtiService para a aplicação
     */
    @Provides
    @Singleton
    fun providePtiService(okHttpClient: OkHttpClient, gson: Gson): SampleService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.API_URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(SampleService::class.java)
    }

}
