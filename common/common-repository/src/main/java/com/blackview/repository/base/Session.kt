package com.blackview.repository.base

import com.blackview.repository.base.RepositoryStore
import com.blackview.repository.base.RepositoryStoreOwner

open class Session :
    RepositoryStoreOwner {

    private val store = RepositoryStore()

    override fun getRepositoryStore(): RepositoryStore {
        return store
    }

    fun clear() {
        store.clear()
    }
}