package com.blackview.repository.base

object RepositoryProviders {

    @JvmStatic
   internal fun of(owner: RepositoryStoreOwner, factory: RepositoryProvider.Factory?): RepositoryProvider {
        val f = factory ?: RepositoryProvider.NewInstanceFactory()
        return RepositoryProvider(
            owner,
            f
        )
    }

    @JvmStatic
    internal fun of(owner: RepositoryStoreOwner): RepositoryProvider {
        return of(
            owner,
            null
        )
    }
}