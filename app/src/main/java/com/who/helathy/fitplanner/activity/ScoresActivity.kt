package com.who.helathy.fitplanner.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.WeightChartUtil
import kotlinx.android.synthetic.main.activity_scores.*
import java.lang.Exception
import java.util.*
import com.github.mikephil.charting.components.AxisBase



class ScoresActivity : AppCompatActivity() {

    private var mWeightPointsList: ArrayList<ChartEnginePoint>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        val weights = DatabaseHelper.getInstance(applicationContext)?.getAllWeighs()
        weights!!.sort()

        mWeightPointsList = WeightChartUtil.convertWeightsToChartEnginePointList(weights)

        this.fillGraph()
    }

    private fun fillGraph() {

        val lineDataSet = LineDataSet(WeightChartUtil.getDataSet(mWeightPointsList), resources.getString(R.string.weightChartTitle))
        lineDataSet.setDrawFilled(true)
        lineDataSet.color = ColorTemplate.getHoloBlue()

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = CustomWeightAxisValueFormatter()

        val graphDescription = Description()
        if (mWeightPointsList!!.count() < 2) {
            graphDescription.text = "${getString(R.string.resultOfDays)} ${getFormattedValue(mWeightPointsList!![0].xValue)}"
            Toast.makeText(this, getString(R.string.poorCountOfWeightValues), Toast.LENGTH_SHORT).show()
        } else {
            graphDescription.text = "${getString(R.string.from)} ${getFormattedValue(xAxis.axisMinimum)} " +
                    "${getString(R.string.to)} ${getFormattedValue(xAxis.axisMaximum)}"
        }
        lineChart.description = graphDescription
        lineChart.animateXY(2000, 2000)
        lineChart.invalidate()
    }

    private fun getFormattedValue(value: Float): String {
        try {
            return mWeightPointsList!!.first{ point -> point.xValue == value}.xLabel.substring(5)
        } catch (e: Exception) {
            return ""
        }
    }

    private inner class CustomWeightAxisValueFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return getFormattedValue(value)
        }
    }
}
