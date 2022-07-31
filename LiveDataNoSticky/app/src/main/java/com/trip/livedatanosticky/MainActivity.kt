package com.trip.livedatanosticky

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.map
import com.bfu.livedata.nosticky.observeNoSticky
import com.trip.livedatanosticky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 给一个初始文案 */
        binding.txtData.text = "--"
        binding.txtNoSticky.text = "--"
        binding.txtData2.text = "--"
        binding.txtNoSticky2.text = "--"

        /* state 场景 */
        viewModel.data.observe(this) {
            binding.txtData.text = "源数据（state）：$it"
        }

        /* event 场景. */
        viewModel.data.observeNoSticky(this) {
            binding.txtNoSticky.text = "源数据（event）：$it"
        }

        /* 因为对 LiveData 零入侵，所以 liveData 可以进行诸如 map、filter、flatmap 等各种变换. */
        val data2 = viewModel.data.map {
            /* 源数据乘以 2 */
            it?.run { this * 2 }
        }

        /* state 场景 */
        data2.observe(this) {
            binding.txtData2.text = "源数据（state）* 2：$it"
        }

        /* event 场景. */
        data2.observeNoSticky(this) {
            binding.txtNoSticky2.text = "源数据（event）* 2：$it"
        }

        /* 如果在 observe* 前 viewModel.data 有初始值，txtData 会收到粘性数据，txtNoSticky 不会. */
        /* 旋转屏幕后，activity 重建, txtData 会收到粘性数据，txtNoSticky 不会. */

        /* 设置点击⌚️. */
        binding.btCount.setOnClickListener {
            viewModel.increment()
        }


    }
}