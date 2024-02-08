package com.example.osmapi.core.common

// Handler that expects just a single element. it can be queried via get().
class SingleElementHandler<T>: Handler<T> {
     var tea: T? = null
    override fun handle(tea: T) {
        this.tea = tea
    }
    fun get(): T? {
        return this.tea
    }
}