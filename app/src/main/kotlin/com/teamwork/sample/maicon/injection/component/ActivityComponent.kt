package com.teamwork.sample.maicon.injection.component

import com.teamwork.sample.maicon.injection.PerActivity
import com.teamwork.sample.maicon.injection.module.ActivityModule
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailActivity
import com.teamwork.sample.maicon.ui.project.list.ProjectListActivity
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: ProjectListActivity)
    fun inject(activity: ProjectDetailActivity)
}
