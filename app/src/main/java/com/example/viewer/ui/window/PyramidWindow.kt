package com.example.viewer.ui.window

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.example.viewer.R
import com.example.viewer.renderer.decimal
import com.example.viewer.renderer.int
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject
import com.example.viewer.renderer.scene.objects.figure.complex.Pyramid
import com.example.viewer.ui.base.BaseWindow

class PyramidWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                    private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {
    override val layoutId = R.layout.add_pyramid

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXPyramid)
        val y = windowView.findViewById<EditText>(R.id.enterYPyramid)
        val z = windowView.findViewById<EditText>(R.id.enterZPyramid)

        val red = windowView.findViewById<EditText>(R.id.enterRedPyramid)
        val blue = windowView.findViewById<EditText>(R.id.enterBluePyramid)
        val green = windowView.findViewById<EditText>(R.id.enterGreenPyramid)

        val v = windowView.findViewById<EditText>(R.id.enterVertNumPyramid)
        val r = windowView.findViewById<EditText>(R.id.enterRadiusPyramid)
        val h = windowView.findViewById<EditText>(R.id.enterHeightPyramid)

        val ambient = windowView.findViewById<EditText>(R.id.enterAmbientPyramid)
        val diffuse = windowView.findViewById<EditText>(R.id.enterDiffusePyramid)
        val specular = windowView.findViewById<EditText>(R.id.enterSpecularPyramid)
        val reflect = windowView.findViewById<EditText>(R.id.enterReflectPyramid)

        return Pyramid(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            h.decimal(),
            r.decimal(),
            v.int(),
            MaterialColor(red.int(), green.int(), blue.int()),
            Material(ambient.decimal(), diffuse.decimal(), specular.decimal(), reflect.decimal())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addPyramid).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}