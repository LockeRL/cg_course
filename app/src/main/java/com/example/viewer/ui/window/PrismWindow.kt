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
import com.example.viewer.renderer.scene.objects.figure.complex.Prism
import com.example.viewer.ui.base.BaseWindow

class PrismWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                  private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {
    override val layoutId = R.layout.add_prism

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXPrism)
        val y = windowView.findViewById<EditText>(R.id.enterYPrism)
        val z = windowView.findViewById<EditText>(R.id.enterZPrism)

        val red = windowView.findViewById<EditText>(R.id.enterRedPrism)
        val blue = windowView.findViewById<EditText>(R.id.enterBluePrism)
        val green = windowView.findViewById<EditText>(R.id.enterGreenPrism)

        val v = windowView.findViewById<EditText>(R.id.enterVertNumPrism)
        val r = windowView.findViewById<EditText>(R.id.enterRadiusPrism)
        val h = windowView.findViewById<EditText>(R.id.enterHeightPrism)

        val ambient = windowView.findViewById<EditText>(R.id.enterAmbientPrism)
        val diffuse = windowView.findViewById<EditText>(R.id.enterDiffusePrism)
        val specular = windowView.findViewById<EditText>(R.id.enterSpecularPrism)
        val reflect = windowView.findViewById<EditText>(R.id.enterReflectPrism)

        return Prism(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            h.decimal(),
            r.decimal(),
            v.int(),
            MaterialColor(red.int(), green.int(), blue.int()),
            Material(ambient.decimal(), diffuse.decimal(), specular.decimal(), reflect.decimal())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addPrism).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}