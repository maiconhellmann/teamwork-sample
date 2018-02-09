package com.teamwork.sample.maicon.ui.project.detail

import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.model.Project
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Created on 08/02/2018.
 */
class ProjectDetailPresenter(val dataManager: DataManager): ProjectDetailContract.Presenter() {
    private var starSubscription: Subscription?= null

    override fun unsubscribe() {starSubscription?.unsubscribe()}

    override fun onOpen(project: Project) {
        view.refreshView()
    }

    override fun onClickStar(project: Project) {
        starSubscription?.unsubscribe()

        project.id?.let {
            view.showProgressIndicator()
            if (project.starred == true) {
                unstarProject(project)
            } else {
                starProject(project)
            }
        }
    }


    fun starProject(project: Project) {
        starSubscription = dataManager.starProject(project.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            project.starred = true
                            view.setProject(project)
                            view.hideProgressIndicator()
                            view.refreshStar()
                        },
                        onError = {
                            project.starred = false
                            view.setProject(project)

                            Timber.e(it)
                            view.hideProgressIndicator()
                            view.showError(it)
                            view.refreshStar()
                        }
                )
    }

    fun unstarProject(project: Project) {
        starSubscription = dataManager.unstarProject(project.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            project.starred = false
                            view.setProject(project)
                            view.hideProgressIndicator()
                            view.refreshStar()
                        },
                        onError = {
                            project.starred=true
                            view.setProject(project)

                            Timber.e(it)
                            view.hideProgressIndicator()
                            view.showError(it)
                            view.refreshStar()
                        }
                )
    }
}