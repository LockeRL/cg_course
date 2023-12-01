package com.example.viewer.ui.window

import android.content.Context
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.example.viewer.R
import com.example.viewer.renderer.decimal
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject
import com.example.viewer.ui.base.BaseWindow

class CameraWindow(context: Context, height: Int, width: Int, private val addObject: (BaseObject) -> Unit,
                   private val adapter: ArrayAdapter<String>
) :
    BaseWindow(context, height, width, addObject, adapter) {

    override val layoutId = R.layout.add_camera

    override fun createObject(): BaseObject {
        val x = windowView.findViewById<EditText>(R.id.enterXCamera)
        val y = windowView.findViewById<EditText>(R.id.enterYCamera)
        val z = windowView.findViewById<EditText>(R.id.enterZCamera)

        val xa = windowView.findViewById<EditText>(R.id.enterXAngleCamera)
        val ya = windowView.findViewById<EditText>(R.id.enterYAngleCamera)
        val za = windowView.findViewById<EditText>(R.id.enterZAngleCamera)
        val zoom = windowView.findViewById<EditText>(R.id.enterZoomCamera)

        return Camera(
            Vector3D(x.decimal(), y.decimal(), z.decimal()),
            Math.toRadians(xa.decimal()),
            Math.toRadians(ya.decimal()),
            Math.toRadians(za.decimal()),
            zoom.decimal()
        )
    }

    override fun applyLogic() {
        windowView.findViewById<Button>(R.id.addCamera).setOnClickListener {
            addObject(createObject())
            adapter.notifyDataSetChanged()
            close()
        }
    }
}