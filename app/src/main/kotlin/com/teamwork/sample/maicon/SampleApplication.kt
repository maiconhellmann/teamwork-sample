package com.teamwork.sample.maicon

import android.annotation.SuppressLint
import android.app.Application
import android.support.annotation.VisibleForTesting
import com.teamwork.sample.maicon.BuildConfig
import com.teamwork.sample.maicon.injection.component.ApplicationComponent
import com.teamwork.sample.maicon.injection.component.DaggerApplicationComponent
import com.teamwork.sample.maicon.injection.module.ApplicationModule
import timber.log.Timber

open class SampleApplication : Application() {


    lateinit var applicationComponent: ApplicationComponent
        private set

    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDaggerComponent()
    }

    @VisibleForTesting
    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
