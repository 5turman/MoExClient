package org.example.moex.ui.share

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.pawegio.kandroid.IntentFor
import kotlinx.android.synthetic.main.activity_share.*
import org.example.moex.R
import org.example.moex.api.Api
import org.example.moex.data.model.Interval
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.koin.android.ext.android.inject

/**
 * Created by 5turman on 6/1/2017.
 */
class ShareActivity : AppCompatActivity() {

    private val api: Api by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shareId = intent.getStringExtra(KEY_ID)
        if (shareId == null) {
            finish()
        }

        setContentView(R.layout.activity_share)

        val fromTill = DateTimeFormat.forPattern("yyyy-MM-dd").print(LocalDate.now().minusDays(1))

//        disposable = api.getCandles(shareId, fromTill, fromTill, Interval.HOUR)
//            .map { dto ->
//                dto.candles.drop(1).mapIndexed { index, item ->
//                    CandleEntry(
//                        index.toFloat(),
//                        item.high.toFloat(),
//                        item.low.toFloat(),
//                        item.open.toFloat(),
//                        item.close.toFloat()
//                    )
//                }
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { entries, error ->
//                if (entries != null) {
//                    val dataSet = CandleDataSet(entries, "Bla-bla-chart")
//                    dataSet.shadowColor = Color.BLACK
//                    dataSet.increasingColor = Color.GREEN
//                    dataSet.decreasingColor = Color.RED
//
//                    candleStickChart.data = CandleData(listOf(dataSet))
//
//                    candleStickChart.xAxis.isEnabled = false
//                    candleStickChart.axisLeft.isEnabled = false
//                    candleStickChart.axisRight.isEnabled = false
//
//                    candleStickChart.description = null
//                    candleStickChart.legend.isEnabled = false
//
//                    candleStickChart.isHighlightPerDragEnabled = false
//                    candleStickChart.isHighlightPerTapEnabled = false
//
//                    candleStickChart.invalidate()
//                }
//                // show error
//                error?.printStackTrace()
//            }
    }

    override fun onDestroy() {
        super.onDestroy()
//        disposable.dispose()
    }

    companion object {
        fun newIntent(context: Context, shareId: String): Intent =
            IntentFor<ShareActivity>(context).putExtra(KEY_ID, shareId)
    }

}

private const val KEY_ID = "id"
