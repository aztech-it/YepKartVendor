package `in`.yepkart.vendor.ui.analytics

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.controls.RoundedBarChart
import `in`.yepkart.vendor.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class AnalyticsFragment : Fragment() {

    private lateinit var analyticsViewModel: AnalyticsViewModel
    private var _binding: FragmentAnalyticsBinding? = null
    private lateinit var barChart: RoundedBarChart
    private val binding get() = _binding!!

    private val days = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        analyticsViewModel = ViewModelProvider(this)[AnalyticsViewModel::class.java]
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        barChart = binding.root.findViewById(R.id.bar_chart)

        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)

        barChart.xAxis.setDrawAxisLine(false)
        barChart.axisLeft.setDrawAxisLine(false)
        barChart.axisRight.setDrawAxisLine(false)

        barChart.axisLeft.setDrawLabels(false)
        barChart.axisRight.setDrawLabels(false)

        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false

        barChart.isDragEnabled = false
        barChart.setPinchZoom(false)
        barChart.isDoubleTapToZoomEnabled = false
        barChart.setScaleEnabled(false)

        barChart.setRadius(100)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        xAxis.yOffset = 10f

        barChart.extraBottomOffset = 5f

        setData(7, 100f)

        barChart.barData.barWidth = 0.5f

        return binding.root
    }

    private fun setData(count: Int, range: Float) {
        val start = 0f
        val values: ArrayList<BarEntry> = ArrayList()
        var i = start.toInt()
        while (i < start + count) {
            val value = (Math.random() * (range + 1)).toFloat()

            if (Math.random() * 100 < 25) {
                values.add(BarEntry(i.toFloat(), value, resources.getDrawable(R.drawable.ic_about)))
            }
            else {
                values.add(BarEntry(i.toFloat(), value))
            }

            i++
        }

        val set1: BarDataSet

        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values

            val color1 = ContextCompat.getColor(binding.root.context, R.color.colorWhite)
            val color2 = ContextCompat.getColor(binding.root.context, R.color.colorAccent)
            set1.color = color1
            set1.highLightColor = color2

            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        }
        else {
            set1 = BarDataSet(values, "")
            set1.setDrawIcons(false)

            val color1 = ContextCompat.getColor(binding.root.context, R.color.colorWhite)
            val color2 = ContextCompat.getColor(binding.root.context, R.color.colorWarningDark)
            set1.color = color1
            set1.highLightColor = color2

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setDrawValues(false)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            barChart.data = data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}