package com.teamwork.sample.maicon

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.test.common.TestDataFactory
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailContract
import com.teamwork.sample.maicon.ui.project.detail.ProjectDetailPresenter
import com.teamwork.sample.maicon.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import rx.Single

@RunWith(MockitoJUnitRunner::class)
class ProjectDetailPresenterTest {
    @Rule @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var view: ProjectDetailContract.View

    @Mock
    lateinit var mockDataManager: DataManager

    lateinit var presenter: ProjectDetailPresenter

    @Before
    fun setUp() {
        presenter = ProjectDetailPresenter(mockDataManager)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun onOpen(){
        val project = TestDataFactory.makeProject1()
        presenter.onOpen(project)

        verify(view).refreshView()
    }

    @Test
    fun starProjectSuccess(){
        val project = TestDataFactory.makeProject1()
        project.starred = false

        whenever(mockDataManager.starProject(project.id!!))
                .thenReturn(Single.just(Response.success(Any())))

        presenter.onClickStar(project)

        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).refreshStar()
        verify(view).setProject(project)
    }

    @Test
    fun starProjectError(){
        val project = TestDataFactory.makeProject1()
        project.starred = false
        val error = Throwable()

        whenever(mockDataManager.starProject(project.id!!))
                .thenReturn(Single.error(error))

        presenter.onClickStar(project)

        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).refreshStar()
        verify(view).showError(error)
    }

    @Test
    fun unstarProjectSuccess(){
        val project = TestDataFactory.makeProject1()
        project.starred = true

        whenever(mockDataManager.unstarProject(project.id!!))
                .thenReturn(Single.just(Response.success(Any())))

        presenter.onClickStar(project)

        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).refreshStar()
        verify(view).setProject(project)
    }

    @Test
    fun unstarProjectError(){
        val project = TestDataFactory.makeProject1()
        project.starred = true
        val error = Throwable()

        whenever(mockDataManager.unstarProject(project.id!!))
                .thenReturn(Single.error(error))

        presenter.onClickStar(project)

        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).refreshStar()
        verify(view).showError(error)
    }
}