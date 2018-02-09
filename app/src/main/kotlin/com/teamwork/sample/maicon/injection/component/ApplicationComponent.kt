package com.teamwork.sample.maicon.injection.component

import android.app.Application
import android.content.Context
import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.remote.service.SampleService
import com.teamwork.sample.maicon.injection.ApplicationContext
import com.teamwork.sample.maicon.injection.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(com.teamwork.sample.maicon.injection.module.ApplicationModule::class, com.teamwork.sample.maicon.injection.module.DataModule::class, com.teamwork.sample.maicon.injection.module.ApiModule::class))
interface ApplicationComponent {
    @ApplicationContext
    fun context(): Context
    fun application(): Application
    fun service(): SampleService
    fun dataManager(): DataManager

    operator fun plus(presenterModule: PresenterModule): ConfigPersistentComponent
}
