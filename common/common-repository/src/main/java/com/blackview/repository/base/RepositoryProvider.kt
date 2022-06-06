package com.blackview.repository.base

import androidx.annotation.MainThread

class RepositoryProvider(private val store: RepositoryStore, private val factory: Factory) {
    constructor(owner: RepositoryStoreOwner, factory: Factory) : this(
        owner.getRepositoryStore(),
        factory
    )

    @MainThread
    fun <T : BaseRepository> get(modelClass: Class<T>): T {
        val canonicalName = modelClass.canonicalName
            ?: throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
        return get("$defaultKey:$canonicalName", modelClass)
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    fun <T : BaseRepository> get(key: String, modelClass: Class<T>): T {
        var repo: BaseRepository? = store.get(key)

        if (modelClass.isInstance(repo)) {
            return repo as T
        }

        repo = factory.create(modelClass)
        store.put(key, repo)

        return repo
    }

    interface Factory {
        fun <T : BaseRepository> create(modelClass: Class<T>): T
    }

    /**
     * Simple factory, which calls empty constructor on the give class.
     */
    open class NewInstanceFactory :
        Factory {

        override fun <T : BaseRepository> create(modelClass: Class<T>): T {

            try {
                return modelClass.newInstance()
            } catch (e: InstantiationException) {
                throw InstantiationException("Cannot create an instance of $modelClass")
            } catch (e: IllegalAccessException) {
                throw IllegalAccessException("Cannot create an instance of $modelClass")
            }
        }
    }

    companion object {
        private const val defaultKey =
            "RepositoryProvider.DefaultKey"
    }
}