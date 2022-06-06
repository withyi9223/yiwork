package com.blackview.repository.session

/**
 *
 * @Description:    AccountSessionManager
 * @Author:         connorTang
 * @CreateDate:     2020/9/15 11:19
 */

object AccountSessionManager {

    private const val VISITOR = "visitor"

    // 初始化默认值，accountId 为 游客
    var accountSession = AccountSession(VISITOR)

    init {

    }

    /**
     *  用户Id是否变更
     */
    fun createNewSession(accountId: String) {
        if (accountSession.accountId != accountId) {
            accountSession.clear()
            accountSession = AccountSession(accountId)
        }
    }
}