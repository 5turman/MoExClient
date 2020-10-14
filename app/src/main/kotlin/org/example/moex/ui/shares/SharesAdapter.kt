package org.example.moex.ui.shares

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import org.example.moex.R
import org.example.moex.core.BaseAdapter
import org.example.moex.core.BindingViewHolder
import org.example.moex.core.toFPx
import org.example.moex.data.model.Share
import org.example.moex.databinding.ItemShareBinding

/**
 * Created by 5turman on 23.03.2017.
 */
class SharesAdapter(private val callback: Callback) :
    BaseAdapter<Share, BindingViewHolder<ItemShareBinding>>() {

    interface Callback {
        fun onShareClicked(share: Share)
    }

    private var upDrawable: Drawable? = null
    private var downDrawable: Drawable? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemShareBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShareBinding.inflate(inflater, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemShareBinding>, position: Int) {
        holder.binding.apply {
            share = getItem(position)
            callback = this@SharesAdapter.callback
            executePendingBindings()
        }
    }

    companion object {
        private var upDrawable: Drawable? = null
        private var downDrawable: Drawable? = null

        @JvmStatic
        fun getChangeDrawable(context: Context, change: Double): Drawable? {
            if (change == 0.0) return null

            val isUp = (change >= 0)
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

}
