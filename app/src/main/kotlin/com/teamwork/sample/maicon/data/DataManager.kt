package com.teamwork.sample.maicon.data

import com.teamwork.sample.maicon.data.model.domainvalues.ProjectStatusEnum
import com.teamwork.sample.maicon.data.remote.service.SampleService


class DataManager (private val service: SampleService) {

    /**
     * Request active user projects
     */
    fun requestActiveProjects() = service.requestProjects(ProjectStatusEnum.ACTIVE)

    /**
     * Star a specific project
     */
    fun starProject(projectId: String) = service.starProject(projectId)

    /**
     * Unstar a specific project
     */
    fun unstarProject(projectId: String) = service.unstarProject(projectId)
}


