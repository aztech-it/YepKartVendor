package `in`.yepkart.vendor.ui.analytics

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.yepkart.vendor.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {

    private lateinit var analyticsViewModel: AnalyticsViewModel
    private var _binding: FragmentAnalyticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        analyticsViewModel =
            ViewModelProvider(this)[AnalyticsViewModel::class.java]

        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}