package org.example.moex.ui.shares

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.example.moex.R
import org.example.moex.core.BaseAdapter
import org.example.moex.core.inflate
import org.example.moex.core.toFPx
import org.example.moex.data.model.Share
import org.example.moex.databinding.ItemShareBinding

/**
 * Created by 5turman on 23.03.2017.
 */
class SharesAdapter(private val callback: Callback) :
    BaseAdapter<Share, SharesAdapter.ViewHolder>() {

    interface Callback {
        fun onClick(position: Int)
    }

    private var upDrawable: Drawable? = null
    private var downDrawable: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_share)
        return ViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val share = getItem(position)
        holder.binding.apply {
            nameTextView.text = share.shortName
            idTextView.text = share.id
            valueTextView.text = share.last.toString()
            changeTextView.apply {
                text = share.lastToPrev.toString()
                setCompoundDrawablesWithIntrinsicBounds(
                    getDrawable(context, share.lastToPrev), null, null, null
                )
            }
            dateTimeTextView.text = TimeFormatter.format(context, share.timestamp)
        }
    }

    class ViewHolder(itemView: View, callback: Callback) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemShareBinding.bind(itemView)!!

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    callback.onClick(position)
                }
            }
        }
    }

    private fun getDrawable(context: Context, value: Double): Drawable? {
        if (value == 0.0) return null

        val isUp = (value >= 0)
        var drawable = if (isUp) upDrawable else downDrawable

        if (drawable == null) {
            val res = context.resources
            val width = res.toFPx(12)
            val height = res.toFPx(10)
            val color = ContextCompat.getColor(context, if (isUp) R.color.up else R.color.down)
            drawable = TriangleDrawable(isUp, width, height, color)

            if (isUp) {
                upDrawable = drawable
            } else {
                downDrawable = drawable
            }
        }

        return drawable
    }

}
