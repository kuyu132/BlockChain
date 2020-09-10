package com.kuyu.blockchain.rxutils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * created by wangguoqun at 2020-09-10
 */

class AutoDisposable : LifecycleObserver {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var lifecycle:Lifecycle
    fun bindTo(lifecycle: Lifecycle) {
        this.lifecycle = lifecycle
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.add(disposable)
        } else {
            throw NotImplementedError("not bind lifecycle")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.dispose()
        this.lifecycle.removeObserver(this)
    }
}

fun Disposable.add(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}