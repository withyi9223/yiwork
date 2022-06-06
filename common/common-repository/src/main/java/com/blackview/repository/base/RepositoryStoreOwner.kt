package com.blackview.repository.base

import com.blackview.repository.base.RepositoryStore

interface RepositoryStoreOwner {
    fun getRepositoryStore(): RepositoryStore
}