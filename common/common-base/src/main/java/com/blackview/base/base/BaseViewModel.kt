package com.blackview.base.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.blackview.util.L
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


open class BaseViewModel : ViewModel() {

    val uiChangeLiveData = UIChangeLiveData()

    //心跳刷新页面
    val heartbeat = SingleLiveEvent<Void>()

    fun showDialog() {
        uiChangeLiveData.showDialogEvent.call()
    }

    fun dismissDialog() {
        uiChangeLiveData.dismissDialogEvent.call()
    }

    fun showToast(msg: String) {
        uiChangeLiveData.toastEvent.postValue(msg)
    }

    fun onBackPressed() {
        uiChangeLiveData.finishEvent.call()
    }

    fun postUiMessageEvent(message: UIMessage) {
        uiChangeLiveData.uiMessageEvent.postValue(message)
    }

    var subscribe: Disposable? = null
    open fun observeLiveData(owner: LifecycleOwner) {
        if (startHeartbeat()) {
            subscribe = Flowable.interval(15, heartbeatPeriod(), TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    heartbeat.call()
                    L.e(it.toString())
                }
        }
    }

    open fun heartbeatPeriod(): Long {
        return 15L
    }

    open fun startHeartbeat(): Boolean {
        return false
    }

    override fun onCleared() {
        super.onCleared()
        subscribe?.dispose()
    }

    companion object {
        class UIChangeLiveData : SingleLiveEvent<Void>() {
            val showDialogEvent = SingleLiveEvent<String>()
            val dismissDialogEvent = SingleLiveEvent<Void>()
            val finishEvent = SingleLiveEvent<Void>()
            val uiMessageEvent = SingleLiveEvent<UIMessage>()
            val toastEvent = SingleLiveEvent<String>()
        }
    }
}