package com.blackview.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils

abstract class BaseMVVMFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    IBaseView {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    var viewModelId = 0

    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<V>(
            inflater,
            initLayoutId(savedInstanceState),
            container,
            false
        )
        viewModel = createViewModel(this)
        //XML的viewModel的绑定
        viewModelId = initVariableId()
        binding.setVariable(viewModelId, viewModel)

        return binding.root
    }

    abstract fun initLayoutId(savedInstanceState: Bundle?): Int

    /**
     * 创建ViewModel 需子类必须实现
     *
     * @param fragment
     * @return VM
     */
    abstract fun createViewModel(fragment: Fragment): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog.Builder(requireContext()).noClose().get()
        initParam()
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initData()

        initViewObservable()

        viewModel.uiChangeLiveData.showDialogEvent.observe(viewLifecycleOwner, Observer {
            if (progressDialog != null) {
                progressDialog?.show()
            } else {
                progressDialog = ProgressDialog.newBuilder(requireActivity()).noClose().get()
                progressDialog?.show()
            }
        })

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(viewLifecycleOwner, Observer {
            progressDialog?.dismiss()
        })
        viewModel.uiChangeLiveData.finishEvent.observe(viewLifecycleOwner, Observer {
            activity?.onBackPressed()
        })
        viewModel.uiChangeLiveData.toastEvent.observe(viewLifecycleOwner, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.uiChangeLiveData.uiMessageEvent.observe(viewLifecycleOwner, Observer {
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
}