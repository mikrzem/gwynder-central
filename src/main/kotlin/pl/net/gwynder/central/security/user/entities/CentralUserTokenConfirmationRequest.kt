package pl.net.gwynder.central.security.user.entities

class CentralUserTokenConfirmationRequest(
        val code: String,
        val applicationName: String
)