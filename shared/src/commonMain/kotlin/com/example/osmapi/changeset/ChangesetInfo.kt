package com.example.osmapi.changeset

import com.example.osmapi.core.changesets.Changeset
import com.example.osmapi.core.map.data.BoundingBox
import com.example.osmapi.core.user.User
import kotlinx.datetime.Instant


class ChangesetInfo(id: Long, user: User) : Changeset(id, user) {
    var tags: MutableMap<String, String> = mutableMapOf()
    var noteCount: Int = 0
    var changesCount: Int = 0
    var boundingBox: BoundingBox = BoundingBox(0.0,0.0,0.0,0.0) // TODO: Make a default one
    var isOpen: Boolean = false
    var discussion: List<ChangesetNote> = mutableListOf()
    var createdAt: Instant = Instant.fromEpochMilliseconds(0)  // Modify later
    var closedAt: Instant = Instant.fromEpochMilliseconds(0)

}