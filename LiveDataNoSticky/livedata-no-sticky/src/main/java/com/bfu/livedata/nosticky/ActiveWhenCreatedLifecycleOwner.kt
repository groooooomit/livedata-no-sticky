package com.bfu.livedata.nosticky

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * 将 owner 的 active 状态从 STARTED 状态扩宽到 CREATED 状态
 *
 * Fragment 或 Activity 处于 CREATED 状态时依然要接收 LiveData 的实时数据时，可以使用此方案解决
 */
fun LifecycleOwner.activeWhenCreated(): LifecycleOwner = ActiveWhenCreateLifecycleOwner(this)

/**
 * 根据当前 owner，构造一个新的 owner，源 owner 处于 CREATED 状态时，新 owner 处于 STARTED 状态（对应 LiveData 的 active 状态）
 */
private class ActiveWhenCreateLifecycleOwner(origin: LifecycleOwner) : LifecycleOwner {

    private val lifecycle = LifecycleRegistry(this)

    init {
        origin.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
            }

            override fun onResume(owner: LifecycleOwner) {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }

            override fun onPause(owner: LifecycleOwner) {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            }
        })
    }

    override fun getLifecycle() = lifecycle

}

