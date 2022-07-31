package com.trip.livedatanosticky

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bfu.livedata.nosticky.observeNoSticky
import com.trip.livedatanosticky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 给一个初始文案 */
        binding.txtData.text = "--"
        binding.txtNoSticky.text = "--"

        /* state 场景 */
        viewModel.data.observe(this) {
            binding.txtData.text = it?.toString()
        }

        /* event 场景. */
        viewModel.data.observeNoSticky(this) {
            binding.txtNoSticky.text = it?.toString()
        }

        /* 如果在 observe* 前 viewModel.data 有初始值，txtData 会收到粘性数据，txtNoSticky 不会. */
        /* 旋转屏幕后，activity 重建, txtData 会收到粘性数据，txtNoSticky 不会. */

        /* 设置点击⌚️. */
        binding.btCount.setOnClickListener {
            viewModel.increment()
        }


    }
}