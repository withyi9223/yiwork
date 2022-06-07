package com.blackview.repository.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.blackview.base.http.ExceptionHandle
import com.blackview.base.request.*
import com.blackview.util.L
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository {

    abstract fun onClean()

    /**
     *  不过滤请求结果
     * @param block 请求体 必须要用suspend关键字修饰
     */
    protected fun <T> httpNoCheck(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> T
    ): LiveData<T> = liveData(context) {
        this.runCatching {
            block()
        }.onSuccess {
            emit(it)
        }.onFailure {
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
        }
    }

    /**
     *  过滤请求结果
     * @param block 请求体 必须要用suspend关键字修饰
     */
    protected fun <T> http(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> BaseResponse<T>
    ): LiveData<BaseResponse<T>> = liveData(context) {
        this.runCatching {
            emit(StartResponse())
            block()
        }.onSuccess {
            emit(
                when (it.code) {
                    0, 200 -> {
                        if (it.data == null) {
                            EmptyResponse()
                        } else {
                            SuccessResponse(it.data!!)
                        }
                    }
                    else -> {
                        L.e("网络错误")
                        FailureResponse(ExceptionHandle.handleException(Throwable("网络错误")))
                    }
                }
            )

        }.onFailure {
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
            emit(FailureResponse(ExceptionHandle.handleException(it)))
        }
    }

}