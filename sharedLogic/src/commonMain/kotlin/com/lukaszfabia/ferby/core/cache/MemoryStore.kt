package com.lukaszfabia.ferby.core.cache

interface MemoryStore<T> {
    fun get(): T?

    fun set(value: T)
}

class MemoryStoreImpl<T> : MemoryStore<T> {
    private var value: T? = null

    override fun get(): T? = value

    override fun set(value: T) {
        this.value = value
    }
}
