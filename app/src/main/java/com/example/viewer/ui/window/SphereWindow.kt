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
import com.example.viewer.renderer.scene.objects.figure.complex.Sphere
import com.example.viewer.ui.base.BaseWindow

class SphereWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                   private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {

    override val layoutId: Int = R.layout.add_sphere

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXSphere)
        val y = windowView.findViewById<EditText>(R.id.enterYSphere)
        val z = windowView.findViewById<EditText>(R.id.enterZSphere)

        val red = windowView.findViewById<EditText>(R.id.enterRedSphere)
        val blue = windowView.findViewById<EditText>(R.id.enterBlueSphere)
        val green = windowView.findViewById<EditText>(R.id.enterGreenSphere)

        val r = windowView.findViewById<EditText>(R.id.enterRadiusSphere)

        val ambient = windowView.findViewById<EditText>(R.id.enterAmbientSphere)
        val diffuse = windowView.findViewById<EditText>(R.id.enterDiffuseSphere)
        val specular = windowView.findViewById<EditText>(R.id.enterSpecularSphere)
        val reflect = windowView.findViewById<EditText>(R.id.enterReflectSphere)

        return Sphere(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            r.decimal(),
            MaterialColor(red.int(), green.int(), blue.int()),
            Material(ambient.decimal(), diffuse.decimal(), specular.decimal(), reflect.decimal())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addSphere).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }


}