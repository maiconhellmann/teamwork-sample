package com.teamwork.sample.maicon.injection.module

import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.remote.service.SampleService
import dagger.Module
import dagger.Provides

@Module
class DbModule {
    @Provides
    fun provideDataManager(service: SampleService)
            = DataManager(service)
}
