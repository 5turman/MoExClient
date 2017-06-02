package org.example.moex.ui.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import org.example.moex.core.toFPx
import org.example.moex.data.model.Share
import java.util.*
import javax.inject.Inject

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartView : View, ChartContract.View {

    @Inject
    lateinit var presenter: ChartContract.Presenter

    private var value: String? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
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
        // TODO draw chart
        value?.let {
            val textWidth = paint.measureText(it)
            canvas.drawText(it, (canvas.width - textWidth) / 2f, canvas.height / 2f, paint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        println("onDetachedFromWindow")
        presenter.detachView()
    }

    override fun add(tick: Share) {
        value = tick.last.toString() + " at " + Date(tick.timestamp).toString()
        invalidate()
    }

}
