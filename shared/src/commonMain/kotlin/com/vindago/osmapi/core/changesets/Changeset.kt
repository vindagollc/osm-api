package com.vindago.osmapi.core.changesets

import com.vindago.osmapi.core.user.User

open class Changeset(var id: Long, var user: User) {
    companion object{
        const val serialVersionUID: Long = 2L
    }
}