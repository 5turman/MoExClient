package org.example.moex.ui.chart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pawegio.kandroid.IntentFor
import org.example.moex.R
import org.example.moex.databinding.ActivityChartBinding
import org.koin.android.ext.android.get

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityChartBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_chart)

        val shareId = intent.getStringExtra(KEY_ID)
        val presenter = ChartPresenter(shareId, get())
        binding.chart.presenter = presenter
        lifecycle.addObserver(presenter)
    }

    companion object {
        fun newIntent(context: Context, shareId: String): Intent =
            IntentFor<ChartActivity>(context).putExtra(KEY_ID, shareId)
    }

}

private const val KEY_ID = "id"
