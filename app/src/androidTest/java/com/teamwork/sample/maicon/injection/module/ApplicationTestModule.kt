package com.teamwork.sample.maicon.injection.module

import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.teamwork.sample.maicon.data.remote.service.SampleService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationTestModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @com.teamwork.sample.maicon.injection.ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    fun service(): SampleService {
        return mock()
    }

    @Provides
    fun databaseHelper(): com.teamwork.sample.maicon.data.local.DatabaseHelper {
        return mock()
    }

    @Provides
    fun dataManager(): com.teamwork.sample.maicon.data.DataManager {
        return mock()
    }
}
