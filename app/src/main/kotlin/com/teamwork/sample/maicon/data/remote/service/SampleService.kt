package com.teamwork.sample.maicon.data.remote.service

import com.teamwork.sample.maicon.data.model.domainvalues.ProjectStatusEnum
import com.teamwork.sample.maicon.data.model.dto.ProjectsDto
import com.teamwork.sample.maicon.data.remote.RemoteConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable
import rx.Single

interface SampleService {

    @GET(RemoteConstants.URL_PROJECTS)
    fun requestProjects(@Query("status") projectStatusEnum: ProjectStatusEnum) : Observable<ProjectsDto>

    @PUT(RemoteConstants.URL_STAR)
    fun starProject(@Path("project_id") projectId: String) : Single<Response<Any>>

    @PUT(RemoteConstants.URL_UNSTAR)
    fun unstarProject(@Path("project_id") projectId: String) : Single<Response<Any>>

}