package com.teamwork.sample.maicon.test.common

import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.data.model.dto.ProjectsDto
import java.util.*

object TestDataFactory {

    @JvmStatic fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    @JvmStatic fun makeProject1(): Project {
        val project = Project()
        project.id = randomUuid()
        project.name = "name1"
        project.startDate = Date()
        project.endDate = Date()
        project.starred = false
        return project
    }

    @JvmStatic fun makeProject2(): Project {
        val project = Project()
        project.id = randomUuid()
        project.name = "name2"
        project.startDate = Date()
        project.endDate = Date()
        project.starred = true
        return project
    }

    @JvmStatic fun makeProjectList() = listOf(makeProject1(), makeProject2())
    @JvmStatic fun makeProjectsDto() = ProjectsDto("OK", makeProjectList())

}
