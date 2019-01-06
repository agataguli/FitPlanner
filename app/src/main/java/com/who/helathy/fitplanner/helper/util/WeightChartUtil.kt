package com.who.helathy.fitplanner.helper.util

import com.github.mikephil.charting.data.Entry
import com.who.helathy.fitplanner.activity.ChartEnginePoint
import com.who.helathy.fitplanner.domain.Weight
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

interface WeightChartUtil {
    companion object {

        fun getXAxisValues(list: ArrayList<ChartEnginePoint>?): ArrayList<String> {
            val xAxis = ArrayList<String>()
            list!!.forEach { point ->
                xAxis.add(point.xLabel)
            }
            return xAxis
        }

        fun getDataSet(list: ArrayList<ChartEnginePoint>?): ArrayList<Entry> {
            val entries = ArrayList<Entry>()

            list!!.forEach { point ->
                entries.add(Entry(point.xValue, point.yValue))
            }
            return entries
        }

        private fun convertWeightToChartEnginePoint(weight: Weight, date: Date): ChartEnginePoint {
            return ChartEnginePoint(xV = this.calculatexV(weight.date!!, date),
                    yV = weight.value.toFloat(), xL = DateUtil.dateToStringShortDateFormat(weight.date!!))
        }

        private fun calculatexV(weightDate: Date, reference30day: Date): Float {
            val difference: Long = weightDate.time - reference30day.time
            val days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
            return (days.toFloat() + 30)
        }

        fun convertWeightsToChartEnginePointList(weights: ArrayList<Weight>?): ArrayList<ChartEnginePoint>? {
            val points = ArrayList<ChartEnginePoint>()

            //hack to get date without timestamp
            val date: Date = DateUtil.shortDateFormatStringToDate(DateUtil.dateToStringShortDateFormat(Date()))

            //hack to get minimum date (current minus 30 days)
            val minimumDate = DateUtil.getDatePlusDays(date, -30)

            weights!!.forEach { weight ->
                if (!weight.date!!.before(minimumDate))  {
                    points.add(convertWeightToChartEnginePoint(weight, date))
                }

            }
            return points
        }
    }
}