package com.example.osmapi.changeset

import com.example.osmapi.core.user.User
import kotlinx.datetime.Instant

data class ChangesetNote(
    var user: User,
    var text: String = "",
    // Created at to be got
    var createdAt: Instant
)