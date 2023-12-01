package com.example.viewer.ui.window

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.example.viewer.R
import com.example.viewer.renderer.decimal
import com.example.viewer.renderer.int
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject
import com.example.viewer.ui.base.BaseWindow

class LightWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                  private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {

    override val layoutId = R.layout.add_light

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXLight)
        val y = windowView.findViewById<EditText>(R.id.enterYLight)
        val z = windowView.findViewById<EditText>(R.id.enterZLight)

        val red = windowView.findViewById<EditText>(R.id.enterRedLight)
        val blue = windowView.findViewById<EditText>(R.id.enterBlueLight)
        val green = windowView.findViewById<EditText>(R.id.enterGreenLight)

        return Light(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            MaterialColor(red.int(), green.int(), blue.int())
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addLight).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}