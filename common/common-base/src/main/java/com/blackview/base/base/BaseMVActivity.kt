package com.blackview.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType

abstract class BaseMVActivity<V : ViewBinding, VM : BaseViewModel > : AppCompatActivity(),
    IBaseView {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    protected var progressDialog: ProgressDialog? = null

    abstract fun getViewBinding(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        setContentView(binding.root)
        onViewCreated()
        progressDialog = ProgressDialog.Builder(this).noClose().get()
        initParam()
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<VM>
    }

    fun onViewCreated() {

        initView()
        initData()
        initViewObservable()

        viewModel.uiChangeLiveData.showDialogEvent.observe(this, Observer {
            if (progressDialog != null) {
                progressDialog?.show()
            } else {
                progressDialog = ProgressDialog.newBuilder(this).noClose().get()
                progressDialog?.show()
            }
        })

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(this, Observer {
            progressDialog?.dismiss()
        })
        viewModel.uiChangeLiveData.toastEvent.observe(this, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.uiChangeLiveData.uiMessageEvent.observe(this, Observer {
            handleEvent(it)
        })

    }

    open fun handleEvent(message: UIMessage) {

    }

    override fun initParam() {
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewObservable() {
        viewModel.observeLiveData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.cancel()
    }

}