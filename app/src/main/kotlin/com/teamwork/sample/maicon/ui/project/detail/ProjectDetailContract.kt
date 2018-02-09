package com.teamwork.sample.maicon.ui.project.detail

import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.ui.base.BasePresenter
import com.teamwork.sample.maicon.ui.base.MvpView

/**
 * Created on 08/02/2018.
 */
class ProjectDetailContract {
    interface View: MvpView{
        fun refreshView()

    }

    abstract class Presenter: BasePresenter<View>(){
        abstract fun onOpen(project: Project)
    }
}