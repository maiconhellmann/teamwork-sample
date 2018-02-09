package com.teamwork.sample.maicon.ui.project.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.teamwork.sample.maicon.R
import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.ui.ViewConstants
import com.teamwork.sample.maicon.ui.base.BaseActivity
import com.teamwork.sample.maicon.util.GlideApp
import com.teamwork.sample.maicon.util.extension.formatToViewDateDefaults
import kotlinx.android.synthetic.main.activity_project_detail.*
import javax.inject.Inject


/**
 * Created on 08/02/2018.
 */
class ProjectDetailActivity: BaseActivity(), ProjectDetailContract.View {

    @Inject lateinit var presenter: ProjectDetailContract.Presenter

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_project_detail)
        this.project = intent.getParcelableExtra(ViewConstants.EXTRA_PROJECT)

        setSupportActionBar(toolbar)
        collapsing_toolbar.title = project.name

        activityComponent.inject(this)
        presenter.attachView(this)

        displayHomeAsUpEnabled()

        presenter.onOpen(project)
    }

    override fun refreshView() {
        project.name?.let{
            setTitle(it)
        }
        project.logo?.let{
            GlideApp.with(this)
                    .load(it)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(imageViewLogo)
        }

        textViewDescription.text = project.description

        textViewDate.text = getString(R.string.date_range,
                project.startDate?.formatToViewDateDefaults(),
                project.endDate?.formatToViewDateDefaults())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun getIntent(context: Context, project: Project): Intent {
            val intent = Intent(context, ProjectDetailActivity::class.java)
            intent.putExtra(ViewConstants.EXTRA_PROJECT, project)

            return intent
        }
    }
}