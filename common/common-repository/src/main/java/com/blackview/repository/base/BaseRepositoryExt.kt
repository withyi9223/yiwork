package com.blackview.repository.base

import com.blackview.base.http.AppException
import com.blackview.base.http.ExceptionHandle
import com.blackview.base.http.executeResponse
import com.blackview.base.request.BaseResponse
import com.blackview.util.L
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*


/**
 * 过滤服务器结果，失败直接抛异常，没有回到异常信息
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 */
fun <T> BaseRepository.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return GlobalScope.launch(Dispatchers.IO) {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure {
                //失败回调
                ExceptionHandle.handleException(it).message?.apply {
                    //打印错误消息
                    L.e(this)
                }
            }
        }.onFailure {
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 */
fun <T> BaseRepository.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return GlobalScope.launch(Dispatchers.IO) {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponseRepois(it) { t ->
                    success(t)
                }
            }.onFailure {
                //失败回调
                error(ExceptionHandle.handleException(it))
                ExceptionHandle.handleException(it).message?.apply {
                    //打印错误消息
                    L.e(this)
                }
            }
        }.onFailure {
            //失败回调
            error(ExceptionHandle.handleException(it))
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
        }
    }
}

/**
 *  不过滤请求结果
 * @param block 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseRepository.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
): Job {
    return GlobalScope.launch(Dispatchers.IO) {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            //成功回调
            success(it)
        }.onFailure {
            //失败回调
            error(ExceptionHandle.handleException(it))
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
        }
    }
}

/**
 *  不过滤请求结果
 * @param block 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 */
fun <T> BaseRepository.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
): Job {
    return GlobalScope.launch(Dispatchers.IO) {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            //成功回调
            success(it)
        }.onFailure {
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                L.e(this)
            }
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponseRepois(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when (response.code) {
            200 -> {
                response.data?.let {
                    success(it)
                }
            }
            else -> {
                throw AppException(response.code, response.message)
            }
        }
    }
}

/**
 *  调用协程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseRepository.launch(
    block: () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    GlobalScope.launch {
        runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}

