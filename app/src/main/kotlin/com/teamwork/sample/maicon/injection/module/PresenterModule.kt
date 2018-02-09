package com.teamwork.sample.maicon.injection.module

import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.injection.ConfigPersistent
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailContract
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailPresenter
import com.teamwork.sample.maicon.ui.project.list.ProjectListContract
import com.teamwork.sample.maicon.ui.project.list.ProjectListPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{
    @Provides
    @ConfigPersistent
    fun provideProjectListPresenter(dataManager: DataManager): ProjectListContract.Presenter {
        return ProjectListPresenter(dataManager)
    }

    @Provides
    @ConfigPersistent
    fun provideProjectDetailPresenter(dataManager: DataManager): ProjectDetailContract.Presenter{
        return ProjectDetailPresenter(dataManager)
    }
}