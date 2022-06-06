package com.blackview.repository.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.AppException
import com.blackview.base.http.HttpRequestCallback
import com.blackview.base.request.BaseResponse


/**
 * 监听 LiveData 的值的变化，回调为 DSL 的形式
 */
inline fun <T> LiveData<BaseResponse<T>>.observeState(
    owner: LifecycleOwner,
    model: BaseViewModel,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    observe(owner, object : IStateObserver<T> {
        override fun onStart() {
            model.uiChangeLiveData.showDialogEvent.post()
            requestCallback.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            requestCallback.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            requestCallback.emptyCallback?.invoke()
        }

        override fun onFailure(e: AppException) {
            requestCallback.failureCallback?.invoke(e)
        }

        override fun onFinish() {
            model.uiChangeLiveData.dismissDialogEvent.post()
            requestCallback.finishCallback?.invoke()
        }
    })
}


