package com.teamwork.sample.maicon.injection.component

import com.teamwork.sample.maicon.injection.module.ApplicationTestModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : com.teamwork.sample.maicon.injection.component.ApplicationComponent
