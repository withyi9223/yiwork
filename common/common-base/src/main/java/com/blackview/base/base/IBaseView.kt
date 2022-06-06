package com.blackview.base.base

interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    fun initParam()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化界面观察者的监听
     */
    fun initViewObservable()

    /**
     * 初始化界面
     */
    fun initView()
}