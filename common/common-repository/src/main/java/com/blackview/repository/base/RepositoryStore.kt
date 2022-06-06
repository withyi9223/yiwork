package com.blackview.repository.base

import com.blackview.repository.base.BaseRepository

class RepositoryStore {

    private val map = HashMap<String, BaseRepository>()

    fun put(key: String, repository: BaseRepository) {
        val repo = map[key]
        repo?.onClean()
        map[key] = repository
    }

    fun get(key: String) = map[key]

    fun clear() {
        for (repository in map.values) {
            repository.onClean()
        }
        map.clear()
    }
}