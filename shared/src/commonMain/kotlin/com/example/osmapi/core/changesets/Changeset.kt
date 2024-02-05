package com.example.osmapi.core.changesets

import com.example.osmapi.core.user.User

class Changeset(val id: Long, val user: User) {
    companion object{
        const val serialVersionUID: Long = 2L
    }
}