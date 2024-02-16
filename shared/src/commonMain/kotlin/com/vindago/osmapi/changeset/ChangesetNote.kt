package com.vindago.osmapi.changeset

import com.vindago.osmapi.core.user.User
import kotlinx.datetime.Instant

/**
 * A post in the changeset discussion. Avoided the wording "changeset comment" here, because
 * this is already what the "commit message" is called in editors
 */
data class ChangesetNote(
    var user: User,
    var text: String = "",
    // Created at to be got
    var createdAt: Instant
)