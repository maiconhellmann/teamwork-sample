package com.teamwork.sample.maicon.injection.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.teamwork.sample.maicon.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class, DbModule::class))
class DataModule {

    /**
     * Prove o sharedPreferences
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(app.getString(R.string.app_name), MODE_PRIVATE)
    }
}
