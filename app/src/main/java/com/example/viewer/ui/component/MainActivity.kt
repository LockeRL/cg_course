package com.example.viewer.ui.component

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.viewer.R
import com.example.viewer.databinding.ViewerLayoutBinding
import com.example.viewer.renderer.decimal
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.ui.base.BaseActivity
import com.example.viewer.ui.base.BaseWindow
import com.example.viewer.ui.window.CameraWindow
import com.example.viewer.ui.window.CubeWindow
import com.example.viewer.ui.window.LightWindow
import com.example.viewer.ui.window.PlaneWindow
import com.example.viewer.ui.window.PrismWindow
import com.example.viewer.ui.window.PyramidWindow
import com.example.viewer.ui.window.SphereWindow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text


class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ViewerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val x = findViewById<EditText>(R.id.enterX)
        val y = findViewById<EditText>(R.id.enterY)
        val z = findViewById<EditText>(R.id.enterZ)
        val zoom = findViewById<EditText>(R.id.enterZoom)
        val ox = findViewById<EditText>(R.id.enterOX)
        val oy = findViewById<EditText>(R.id.enterOY)
        val oz = findViewById<EditText>(R.id.enterOZ)

        val objectSelector = findViewById<Spinner>(R.id.objectsSpinner)

        val mainCameraText = findViewById<EditText>(R.id.mainCamera)
        val activeItemText = findViewById<EditText>(R.id.activeItem)
        val objectsList = findViewById<ListView>(R.id.objectsList)
        val objectListAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.objectsArray)
        objectsList.adapter = objectListAdapter

        objectsList.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            if ((viewModel.activeItem == i) and (viewModel.isCamera(i))) {
                viewModel.setMainCamera()
                mainCameraText.setText((view as TextView).text.toString())
            }
            viewModel.activeItem = i
            activeItemText.setText((view as TextView).text.toString())
        }


        viewModel.initCanvas(screenWidth, screenHeight)

        viewModel.addObject(Scene.defaultCamera)
        viewModel.setMainCamera()

        viewModel.canvas
            .distinctUntilChanged { old, new ->
                old?.generationId == new?.generationId
            }.onEach { canvas ->
                binding.canvas.setImageBitmap(canvas)
            }.launchIn(lifecycleScope)

        binding.buttonMove.setOnClickListener {
            val vec = Vector3D(x.decimal(), y.decimal(), z.decimal())
            viewModel.moveObject(vec)

//            viewModel.act()
        }

        binding.buttonRotate.setOnClickListener {
            val vec = Vector3D(ox.decimal(), oy.decimal(), oz.decimal())
            viewModel.rotateObject(vec)
        }

        binding.buttonZoom.setOnClickListener {
            viewModel.changeZoom(zoom.decimal())
        }

        binding.buttonAdd.setOnClickListener {
            val width = (screenWidth * WIDTH_K).toInt()
            val height = (screenHeight * HEIGHT_K).toInt()
            val window: BaseWindow? = when (objectSelector.selectedItem) {
                getString(R.string.sphere) -> SphereWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.cube) -> CubeWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.light) -> LightWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.prism) -> PrismWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.pyramid) -> PyramidWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.plane) -> PlaneWindow(this, height, width, viewModel::addObject, objectListAdapter)
                getString(R.string.camera) -> CameraWindow(this, height, width, viewModel::addObject, objectListAdapter)
                else -> null
            }
            window?.open(findViewById(R.id.main_layout))
            window?.applyLogic()
        }

        binding.clearObjects.setOnClickListener {
            viewModel.clearObjects()
            objectListAdapter.notifyDataSetChanged()
        }

        binding.deleteObject.setOnClickListener {
            viewModel.deleteObject()
            objectListAdapter.notifyDataSetChanged()
        }

    }

    override fun initViewBinding() {
        binding = ViewerLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private companion object {
        const val WIDTH_K = 0.6
        const val HEIGHT_K = 1
    }

}
