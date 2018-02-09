package com.teamwork.sample.maicon.ui.project.list

import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.model.Project
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers
import timber.log.Timber

class ProjectListPresenter(val dataManager: DataManager): ProjectListContract.Presenter() {
    private var subscription: Subscription? = null

    override fun unsubscribe() {
        subscription?.unsubscribe()
    }


    override fun requestProjects() {
        subscription?.unsubscribe()

        view.showProgressIndicator()

        subscription = dataManager.requestActiveProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            view.hideProgressIndicator()

                            Timber.e("On next $it")

                            if(it.projects.isNotEmpty()){
                                view.refreshProjectList(it.projects)
                            }else{
                                view.showEmptyProjectList()
                            }
                        },
                        onError = {
                            Timber.e(it)
                            view.hideProgressIndicator()
                            view.showEmptyProjectList()
                            view.showError(it)
                        }
                )
    }

    override fun onClickProject(project: Project) {
        view.showProjectDetails(project)
    }
}