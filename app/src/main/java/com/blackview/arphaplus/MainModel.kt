package com.blackview.arphaplus

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.apiService
import com.blackview.repository.apiService2
import com.blackview.repository.repository.RepositoryFactory
import com.blackview.util.L

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/5/30.
 */
class MainModel : BaseViewModel() {

    //持久性的 
    //private val repository = RepositoryFactory.createByAccountSession(MainRepository::class.java)

    //跟随当前model的生命周期
    private val repository by lazy { MainRepository() }


    val string = ObservableField<String>()

    //要输入的参数
    var phoneNumber = MutableLiveData<String>()

    //必须要通过switchMap 中转 整个链条是通过LiveData串联的
    var livePhone = phoneNumber.switchMap {
        repository.getData(it)
    }


    //直接跳过repository
    fun getData(string: String) {
        request({ apiService2.phoneAddress(url, string) }, {
            this.string.set(it.province + it.city + it.sp)
        })
    }


}