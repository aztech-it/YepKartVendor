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
import android.graphics.Color
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData

import com.github.mikephil.charting.data.PieDataSet

class AnalyticsFragment : Fragment() {

    private lateinit var analyticsViewModel: AnalyticsViewModel
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var barChart: RoundedBarChart
    private lateinit var pieChart: PieChart

    private val days = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        analyticsViewModel = ViewModelProvider(this)[AnalyticsViewModel::class.java]
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        barChart = binding.root.findViewById(R.id.bar_chart)
        pieChart = binding.root.findViewById(R.id.pie_chart)

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

        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setHoleColor(ContextCompat.getColor(binding.root.context, R.color.colorAccentAlt));
        pieChart.holeRadius = 30f
        pieChart.isRotationEnabled = false

        setBarData()
        setPieData()

        barChart.barData.barWidth = 0.5f

        return binding.root
    }

    private fun setBarData() {
        val values: ArrayList<BarEntry> = ArrayList()

        values.add(BarEntry(0f, 1129f))
        values.add(BarEntry(1f, 890f))
        values.add(BarEntry(2f, 1240f))
        values.add(BarEntry(3f, 1062f))
        values.add(BarEntry(4f, 644f))
        values.add(BarEntry(5f, 1150f))
        values.add(BarEntry(6f, 1360f))

        val set: BarDataSet

        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            set = barChart.data.getDataSetByIndex(0) as BarDataSet
            set.values = values

            val color1 = ContextCompat.getColor(binding.root.context, R.color.colorSuccessDark)
            val color2 = ContextCompat.getColor(binding.root.context, R.color.colorSuccessDark)
            set.color = color1
            set.highLightColor = color2

            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        }
        else {
            set = BarDataSet(values, "")
            set.setDrawIcons(false)

            val color1 = ContextCompat.getColor(binding.root.context, R.color.colorSuccessDark)
            val color2 = ContextCompat.getColor(binding.root.context, R.color.colorSuccessDark)
            set.color = color1
            set.highLightColor = color2

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set)
            val data = BarData(dataSets)
            data.setDrawValues(false)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            barChart.data = data
        }
    }

    private fun setPieData() {
        val values: ArrayList<PieEntry> = ArrayList()

        val set: MutableMap<String, Int> = HashMap()
        set["NOVEMBER"] = 200
        set["DECEMBER"] = 230
        set["JANUARY"] = 100
        set["FEBRUARY"] = 400

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#CD982C"))
        colors.add(Color.parseColor("#049BB9"))
        colors.add(Color.parseColor("#5B919E"))
        colors.add(Color.parseColor("#F9B400"))

        for (type in set.keys) {
            values.add(PieEntry(set[type]!!.toFloat(), type))
        }

        val pieDataSet = PieDataSet(values, "")
        pieDataSet.colors = colors
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        pieData.setValueTextSize(12f)

        pieChart.data = pieData
        pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}