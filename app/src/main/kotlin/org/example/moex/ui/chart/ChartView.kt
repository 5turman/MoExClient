package org.example.moex.ui.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import org.example.moex.core.Ring
import org.example.moex.core.toFPx
import org.example.moex.core.toPx
import org.example.moex.data.model.Share
import javax.inject.Inject

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartView : View, ChartContract.View {

    @Inject
    lateinit var presenter: ChartContract.Presenter

    private val ring = Ring<Share>(10)
    private val path = Path()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.textSize = context.resources.toFPx(16)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.attachView(this)
    }

    override fun onDraw(canvas: Canvas) {

        val width = canvas.width
        val height = canvas.height

        val count = ring.count()
        if (count > 0) {

            val radius = context.resources.toFPx(4)

            if (count == 1) {
                canvas.drawCircle(width / 2f, height / 2f, radius, paint)
                return
            }

            var min = Double.MAX_VALUE
            var max = Double.MIN_VALUE

            var index = ring.getStart()
            val endIndex = index + count
            while (index < endIndex) {
                val share = ring.get(index)

                min = Math.min(min, share.last)
                max = Math.max(max, share.last)

                ++index
            }

            min = Math.floor(min)
            max = Math.ceil(max)


            val offset = context.resources.toPx(8)

            val step = (width - 2 * offset) / (count - 1)
            val h = height - 2 * offset

            val bottomY = height - offset // min

            canvas.drawLine(0f, offset.toFloat(), width.toFloat(), offset.toFloat(), paint)

            val fontMetrics = paint.fontMetrics
            canvas.drawText(max.toString(), offset.toFloat(), offset.toFloat() - fontMetrics.top, paint)

            canvas.drawLine(0f, offset.toFloat() + h, width.toFloat(), offset.toFloat() + h, paint)
            canvas.drawText(min.toString(), offset.toFloat(), height - offset.toFloat() - fontMetrics.bottom, paint)

            var cx = offset.toFloat()

            var lastCx = -1f
            var lastCy = -1f

            var num = 0

            path.reset()

            index = ring.getStart()
            while (index < endIndex) {
                val share = ring.get(index)

                val cy = (bottomY - (share.last - min) * h / (max - min)).toFloat()

                canvas.drawCircle(cx, cy, radius, paint)

                if (num > 0) {
                    path.lineTo(cx, cy)
//                    if (num > 1) {
//
//                    }
                } else {
                    path.moveTo(cx, cy)
                }
                ++num

//                if (lastCx >= 0f) {
//                    canvas.drawLine(lastCx, lastCy, cx, cy, paint)
//                }
                lastCx = cx
                lastCy = cy

                cx += step
                ++index

                if (index == endIndex) {
                    val title = share.last.toString()
                    val w = paint.measureText(title)

                    canvas.drawLine(0f, cy, width.toFloat(), cy, paint)
                    canvas.drawText(title, width - offset - w, cy - fontMetrics.bottom, paint)
                }
            }

            canvas.drawPath(path, pathPaint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        println("onDetachedFromWindow")
        presenter.detachView()
    }

    override fun add(tick: Share) {
        ring.add(tick)
        invalidate()
    }

}
