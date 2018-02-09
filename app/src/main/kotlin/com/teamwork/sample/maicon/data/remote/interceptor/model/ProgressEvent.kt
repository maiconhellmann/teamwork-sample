package com.teamwork.sample.maicon.data.remote.interceptor.model

class ProgressEvent(val contentLength: Long,
                    val bytesRead: Long) {

    val progress: Int

    init {
        this.progress = (bytesRead / (contentLength / 100f)).toInt()
    }

    fun percentIsAvailable(): Boolean {
        return contentLength > 0
    }
}