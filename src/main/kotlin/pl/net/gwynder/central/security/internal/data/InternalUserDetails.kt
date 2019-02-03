package pl.net.gwynder.central.security.internal.data

import pl.net.gwynder.central.security.services.CommonUserDetails

class InternalUserDetails : CommonUserDetails(
        "[internal]",
        listOf("INTERNAL")
)