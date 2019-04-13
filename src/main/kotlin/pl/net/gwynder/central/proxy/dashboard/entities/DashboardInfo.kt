package pl.net.gwynder.central.proxy.dashboard.entities

import com.fasterxml.jackson.annotation.JsonCreator

class DashboardInfo @JsonCreator constructor(
        val title: String,
        val rows: List<DashboardInfoRow>
)

class DashboardInfoRow @JsonCreator constructor(
        val description: String,
        val content: String
)