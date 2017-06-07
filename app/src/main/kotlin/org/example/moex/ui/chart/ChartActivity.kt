package org.example.moex.ui.chart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pawegio.kandroid.IntentFor
import kotlinx.android.synthetic.main.activity_chart.*
import org.example.moex.App
import org.example.moex.R

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartActivity : AppCompatActivity() {

    companion object {

        const val KEY_ID = "id"

        fun newIntent(context: Context, shareId: String): Intent =
                IntentFor<ChartActivity>(context).putExtra(KEY_ID, shareId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chart)

        val shareId = intent.getStringExtra(KEY_ID)
        App.getComponent(this).chartComponent(ChartModule(shareId)).inject(chart)
    }

    override fun onStart() {
        super.onStart()
        chart.presenter.onStart()
    }

    override fun onStop() {
        chart.presenter.onStop()
        super.onStop()
    }

}
