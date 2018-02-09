package com.teamwork.sample.maicon

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.teamwork.sample.maicon.data.model.dto.ProjectsDto
import com.teamwork.sample.maicon.test.common.TestDataFactory
import com.teamwork.sample.maicon.ui.project.list.ProjectListContract
import com.teamwork.sample.maicon.ui.project.list.ProjectListPresenter
import com.teamwork.sample.maicon.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable

@RunWith(MockitoJUnitRunner::class)
class ProjectListPresenterTest {
    @Rule @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var mockView: ProjectListContract.View

    @Mock
    lateinit var mockDataManager: com.teamwork.sample.maicon.data.DataManager

    lateinit var presenter: ProjectListPresenter

    @Before
    fun setUp() {
        presenter = ProjectListPresenter(mockDataManager)
        presenter.attachView(mockView)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun requestProjectSuccess(){
        val dto = TestDataFactory.makeProjectsDto()
        whenever(mockDataManager.requestActiveProjects())
                .thenReturn(Observable.just(dto))

        presenter.requestProjects()

        verify(mockView).showProgressIndicator()
        verify(mockView).hideProgressIndicator()
        verify(mockView).refreshProjectList(dto.projects)
    }

    @Test
    fun requestProjectError(){
        val error = Throwable()
        whenever(mockDataManager.requestActiveProjects())
                .thenReturn(Observable.error(error))

        presenter.requestProjects()

        verify(mockView).showProgressIndicator()
        verify(mockView).hideProgressIndicator()
        verify(mockView).showError(error)
        verify(mockView).showEmptyProjectList()
    }

    @Test
    fun requestProjectNoResults(){

        val dto = ProjectsDto("OK",listOf())
        whenever(mockDataManager.requestActiveProjects())
                .thenReturn(Observable.just(dto))

        presenter.requestProjects()

        verify(mockView).showProgressIndicator()
        verify(mockView).hideProgressIndicator()
        verify(mockView).showEmptyProjectList()
    }

}