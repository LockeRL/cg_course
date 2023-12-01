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
import com.example.viewer.renderer.scene.objects.figure.complex.Plane
import com.example.viewer.ui.base.BaseWindow

class PlaneWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                  private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {

    override val layoutId = R.layout.add_plane

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXPlane)
        val y = windowView.findViewById<EditText>(R.id.enterYPlane)
        val z = windowView.findViewById<EditText>(R.id.enterZPlane)

        val red = windowView.findViewById<EditText>(R.id.enterRedPlane)
        val blue = windowView.findViewById<EditText>(R.id.enterBluePlane)
        val green = windowView.findViewById<EditText>(R.id.enterGreenPlane)

        val l = windowView.findViewById<EditText>(R.id.enterLengthPlane)
        val w = windowView.findViewById<EditText>(R.id.enterWidthPlane)

        val ambient = windowView.findViewById<EditText>(R.id.enterAmbientPlane)
        val diffuse = windowView.findViewById<EditText>(R.id.enterDiffusePlane)
        val specular = windowView.findViewById<EditText>(R.id.enterSpecularPlane)
        val reflect = windowView.findViewById<EditText>(R.id.enterReflectPlane)

        return Plane(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            l.decimal(),
            w.decimal(),
            MaterialColor(red.int(), green.int(), blue.int()),
            Material(ambient.decimal(), diffuse.decimal(), specular.decimal(), reflect.decimal())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addPlane).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}