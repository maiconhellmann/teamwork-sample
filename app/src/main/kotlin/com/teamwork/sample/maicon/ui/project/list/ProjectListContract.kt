package com.teamwork.sample.maicon.ui.project.list

import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.ui.base.BasePresenter
import com.teamwork.sample.maicon.ui.base.MvpView

class ProjectListContract {
    interface View: MvpView {
        fun showEmptyProjectList()
        fun refreshProjectList(projects: List<Project>)
        fun showProjectDetails(project: Project)

    }
    abstract class Presenter: BasePresenter<View>(){
        abstract fun requestProjects()
        abstract fun onClickProject(project: Project)
    }
}