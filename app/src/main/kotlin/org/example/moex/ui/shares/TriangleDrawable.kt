package org.example.moex.ui.shares

import android.graphics.Path
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.graphics.drawable.shapes.Shape

/**
 * Created by 5turman on 01.04.2017.
 */
class TriangleDrawable(isUp: Boolean, width: Float, height: Float, color: Int) : ShapeDrawable() {

    init {
        val path = Path()
        if (isUp) {
            path.moveTo(width / 2, 0f)
            path.lineTo(width, height)
            path.lineTo(0f, height)
            path.close()
        } else {
            path.lineTo(width, 0f)
            path.lineTo(width / 2, height)
            path.close()
        }

        shape = PathShape(path, width, height) as Shape
        paint.color = color

        intrinsicWidth = width.toInt()
        intrinsicHeight = height.toInt()
    }

}
