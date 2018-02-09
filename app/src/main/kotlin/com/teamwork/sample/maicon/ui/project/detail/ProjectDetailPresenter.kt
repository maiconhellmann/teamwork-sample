package com.teamwork.sample.maicon.ui.project.detail

import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.model.Project

/**
 * Created on 08/02/2018.
 */
class ProjectDetailPresenter(val dataManager: DataManager): ProjectDetailContract.Presenter() {
    override fun unsubscribe() {}

    override fun onOpen(project: Project) {
        view.refreshView()
    }
}