package com.teamwork.sample.maicon.injection.component

import com.teamwork.sample.maicon.injection.ConfigPersistent
import com.teamwork.sample.maicon.injection.module.ActivityModule
import com.teamwork.sample.maicon.injection.module.PresenterModule
import dagger.Subcomponent

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [BaseActivity] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@com.teamwork.sample.maicon.injection.ConfigPersistent
@Subcomponent(modules = arrayOf(PresenterModule::class))
interface ConfigPersistentComponent {
    operator fun plus(activityModule: ActivityModule): ActivityComponent

    //inject fragments
    //fun inject (fragment: Fragment)
}
