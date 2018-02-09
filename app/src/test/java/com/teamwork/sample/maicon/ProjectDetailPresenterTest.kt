package com.teamwork.sample.maicon

import com.nhaarman.mockito_kotlin.verify
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

@RunWith(MockitoJUnitRunner::class)
class ProjectDetailPresenterTest {
    @Rule @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var mockView: ProjectDetailContract.View

    @Mock
    lateinit var mockDataManager: DataManager

    lateinit var presenter: ProjectDetailPresenter

    @Before
    fun setUp() {
        presenter = ProjectDetailPresenter(mockDataManager)
        presenter.attachView(mockView)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun onOpen(){
        val project = TestDataFactory.makeProject1()
        presenter.onOpen(project)

        verify(mockView).refreshView()
    }

}