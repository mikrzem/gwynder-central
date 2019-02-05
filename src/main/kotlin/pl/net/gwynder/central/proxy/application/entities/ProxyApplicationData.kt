package pl.net.gwynder.central.proxy.application.entities

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyApplicationData @JsonCreator constructor(
        val name: String,
        val path: String,
        val displayName: String
)