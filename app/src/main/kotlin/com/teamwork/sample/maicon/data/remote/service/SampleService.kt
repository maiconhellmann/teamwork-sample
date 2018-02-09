package com.teamwork.sample.maicon.data.remote.service

import com.teamwork.sample.maicon.data.model.domainvalues.ProjectStatusEnum
import com.teamwork.sample.maicon.data.model.dto.ProjectsDto
import com.teamwork.sample.maicon.data.remote.RemoteConstants
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface SampleService {

    @GET(RemoteConstants.URL_PROJECTS)
    fun requestProjects(@Query("status") projectStatusEnum: ProjectStatusEnum) : Observable<ProjectsDto>
}