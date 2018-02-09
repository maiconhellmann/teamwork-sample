package com.teamwork.sample.maicon.injection.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @com.teamwork.sample.maicon.injection.PerActivity
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @com.teamwork.sample.maicon.injection.PerActivity
    @com.teamwork.sample.maicon.injection.ActivityContext
    internal fun providesContext(): Context {
        return activity
    }

    @Provides
    internal fun provideFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }

}
