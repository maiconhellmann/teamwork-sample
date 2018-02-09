package com.teamwork.sample.maicon.ui.project.list

import android.content.Intent
import android.os.Bundle
import com.teamwork.sample.maicon.R
import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.ui.ViewConstants
import com.teamwork.sample.maicon.ui.base.BaseActivity
import com.teamwork.sample.maicon.ui.base.BasePresenter
import com.teamwork.sample.maicon.ui.base.MvpView
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailActivity
import com.teamwork.sample.maicon.util.extension.visible
import kotlinx.android.synthetic.main.activity_project_list.*
import javax.inject.Inject


class ProjectListActivity : BaseActivity(), ProjectListContract.View {
    @Inject lateinit var presenter: ProjectListContract.Presenter

    @Inject lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_project_list)

        activityComponent.inject(this)
        presenter.attachView(this)

        addEvents()

        recyclerView.adapter = adapter
        presenter.requestProjects()

        textViewQuantity.text = getString(R.string.no_result)
        setTitle(R.string.projects)
    }

    private fun addEvents() {
        adapter.clickEvent.subscribe({
            presenter.onClickProject(it)
        })
    }

    override fun showEmptyProjectList() {
        layoutNoResultsFound.visible()
    }

    override fun refreshProjectList(projects: List<Project>) {
        adapter.dataList = projects.toMutableList()
        textViewQuantity.text = getString(R.string.results_found, projects.size)
    }

    override fun showProjectDetails(project: Project) {
        startActivityForResult(ProjectDetailActivity.getIntent(this, project), ViewConstants.REQUES_CODE_DETAIL)
    }

    override fun getPresenter(): BasePresenter<out MvpView>? {
        return presenter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ViewConstants.REQUES_CODE_DETAIL){
            presenter.requestProjects()
        }
    }
}