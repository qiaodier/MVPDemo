package com.mvp.cn.mvp.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.mvp.cn.R
import com.mvp.master.keep.foreground.ForegroundService
import com.mvp.master.mvp.base.BaseFragment

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.concurrent.TimeUnit

/**
 *@author qiaohao
 *@date 21-3-30 上午10:45
 */
class FirstFragment : BaseFragment() {
//    private var buttonStart:Button?=null
//    private var buttonStop:Button?=null
    private var disposable: Disposable? = null;
    override fun initViews(view: View?) {
        Log.e("FirstFragment", "initViews")
//        buttonStop = view?.findViewById(R.id.button_stop) as Button
//        buttonStart = view.findViewById(R.id.button_start) as Button
        button_start?.setOnClickListener {
            Log.e("FirstFragment", "setOnClickListener")
            disposable = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.e("FirstFragment", "${it}")
                        textView_count.text = "${it}"
                    }, {
                        Log.e("FirstFragment", "" + it)
                        textView_count.text=""
                    })
        }
        button_stop?.setOnClickListener {
            disposable?.dispose()
        }
        activity?.startService(Intent(activity,ForegroundService::class.java))
        98.79+73.60
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_first
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }
}