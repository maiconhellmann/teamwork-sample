package com.teamwork.sample.maicon.data

import com.teamwork.sample.maicon.data.model.domainvalues.ProjectStatusEnum
import com.teamwork.sample.maicon.data.remote.service.SampleService


class DataManager (private val service: SampleService) {

    /**
     * Request all projects
     */
    fun requestActiveProjects() = service.requestProjects(ProjectStatusEnum.ACTIVE)

}


