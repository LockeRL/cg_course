package com.example.viewer.ui.component

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.viewer.databinding.ViewerLayoutBinding
import com.example.viewer.ui.base.BaseActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ViewerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initCanvas(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels
        )

        viewModel.canvas
            .distinctUntilChanged { old, new ->
                old?.generationId == new?.generationId
            }.onEach { canvas ->
                binding.canvas.setImageBitmap(canvas)
            }.launchIn(lifecycleScope)


        binding.button.setOnClickListener {
            viewModel.change()
            viewModel.act()
        }



    }

    override fun initViewBinding() {
        binding = ViewerLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}
