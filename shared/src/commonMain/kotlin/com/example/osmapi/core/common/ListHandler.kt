package com.example.osmapi.core.common

class ListHandler<T>: Handler<T> {

    private var list: MutableList<T> = mutableListOf()

    override fun handle(tea: T) {
        this.list.add(tea)
    }

    fun get(): List<T> {
        return this.list
    }
}