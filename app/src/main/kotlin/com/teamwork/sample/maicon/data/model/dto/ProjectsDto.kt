package com.teamwork.sample.maicon.data.model.dto

import com.google.gson.annotations.SerializedName
import com.teamwork.sample.maicon.data.model.Project

class ProjectsDto(
        @SerializedName("STATUS") val status: String,
        val projects: List<Project>
)