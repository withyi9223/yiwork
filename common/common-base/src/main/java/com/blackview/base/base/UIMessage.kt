package com.blackview.base.base


data class UIMessage(
    val code: Int = 0,
    val msg: String = "",
    val arg1: Int = 0,
    val arg2: Int = 0,
    val obj: Any? = null
)