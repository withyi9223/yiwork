package com.blackview.arphaplus

import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVVMActivity
import com.blackview.repository.base.observeState
import com.blackview.repository.session.AccountSessionManager
import com.blackview.util.L
import com.blackview.util.gotoAct

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainModel>() {


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.mainModel
    }


    override fun initView() {
        super.initView()
        L.e(AccountSessionManager.accountSession.accountId)
        binding.btnClick.setOnClickListener {
            //第一种方式
            //viewModel.getData("13800138000")
            //第二种
            viewModel.phoneNumber.postValue("13800138000")

        }

        binding.tvHelloWorld.setOnClickListener {
            gotoAct<DemoActivity>()
        }


    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.livePhone.observeState(this, viewModel) {
            onSuccess {
                viewModel.string.set(it.province+it.city+it.sp)
            }
            onFailure {
               viewModel.showToast(it.message!!)
            }
        }
    }

}