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
import com.example.viewer.renderer.scene.objects.figure.complex.Cube
import com.example.viewer.ui.base.BaseWindow

class CubeWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit, private val adapter: ArrayAdapter<String>) :
    BaseWindow(context, height, width, addObject, adapter) {
    override val layoutId = R.layout.add_cube

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXCube)
        val y = windowView.findViewById<EditText>(R.id.enterYCube)
        val z = windowView.findViewById<EditText>(R.id.enterZCube)

        val red = windowView.findViewById<EditText>(R.id.enterRedCube)
        val blue = windowView.findViewById<EditText>(R.id.enterBlueCube)
        val green = windowView.findViewById<EditText>(R.id.enterGreenCube)

        val l = windowView.findViewById<EditText>(R.id.enterLengthCube)
        val w = windowView.findViewById<EditText>(R.id.enterWidthCube)
        val h = windowView.findViewById<EditText>(R.id.enterHeightCube)

        val ambient = windowView.findViewById<EditText>(R.id.enterAmbientCube)
        val diffuse = windowView.findViewById<EditText>(R.id.enterDiffuseCube)
        val specular = windowView.findViewById<EditText>(R.id.enterSpecularCube)
        val reflect = windowView.findViewById<EditText>(R.id.enterReflectCube)

        return Cube(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            l.decimal(),
            w.decimal(),
            h.decimal(),
            MaterialColor(red.int(), green.int(), blue.int()),
            Material(ambient.decimal(), diffuse.decimal(), specular.decimal(), reflect.decimal())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addCube).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}