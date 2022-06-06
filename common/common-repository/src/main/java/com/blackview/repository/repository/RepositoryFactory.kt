package com.blackview.repository.repository

import com.blackview.repository.base.BaseRepository
import com.blackview.repository.base.RepositoryProviders
import com.blackview.repository.session.AccountSessionManager
import com.blackview.repository.session.AppSessionManager

/**
 *
 * @Description:    RepositoryFactory
 * @Author:         connorTang
 * @CreateDate:     2020/9/15 11:25
 */

object RepositoryFactory {

    /**
     *  创建跟随APP生命周期的数据仓库
     */
    fun <T : BaseRepository> createByAppSession(modelClass: Class<T>): T {
        return RepositoryProviders.of(AppSessionManager.appSession).get(modelClass)
    }

    /**
     * 创建跟随用户生命周期的数据仓库
     */
    fun <T : BaseRepository> createByAccountSession(modelClass: Class<T>): T {
        return RepositoryProviders.of(AccountSessionManager.accountSession).get(modelClass)
    }
}