package com.teamwork.sample.maicon

import com.nhaarman.mockito_kotlin.whenever
import com.teamwork.sample.maicon.data.DataManager
import com.teamwork.sample.maicon.data.model.domainvalues.ProjectStatusEnum
import com.teamwork.sample.maicon.data.model.dto.ProjectsDto
import com.teamwork.sample.maicon.data.remote.service.SampleService
import com.teamwork.sample.maicon.test.common.TestDataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.observers.TestSubscriber

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Mock
    lateinit var mockService: SampleService

    lateinit var dataManager: DataManager

    @Before
    fun setUp() {
        dataManager = com.teamwork.sample.maicon.data.DataManager(mockService)
    }

    @Test
    fun requestProjectsSuccess() {
        val dto = TestDataFactory.makeProjectsDto()

        whenever(mockService.requestProjects(ProjectStatusEnum.ACTIVE))
                .thenReturn(Observable.just(dto))

        val result = TestSubscriber<ProjectsDto>()
        dataManager.requestActiveProjects().subscribe(result)

        result.assertNoErrors()
        result.assertCompleted()
    }
    @Test
    fun requestProjectsError() {
        val error = Throwable()

        whenever(mockService.requestProjects(ProjectStatusEnum.ACTIVE))
                .thenReturn(Observable.error(error))

        val result = TestSubscriber<ProjectsDto>()
        dataManager.requestActiveProjects().subscribe(result)

        result.assertError(error)
    }

}
