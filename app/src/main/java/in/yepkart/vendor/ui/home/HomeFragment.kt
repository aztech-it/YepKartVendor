package `in`.yepkart.vendor.ui.home

import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.adapters.JobAdapter
import `in`.yepkart.vendor.controls.ExpandableHeightListView
import `in`.yepkart.vendor.databinding.FragmentHomeBinding
import `in`.yepkart.vendor.enums.JobAction
import `in`.yepkart.vendor.models.JobItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var listUpcoming: ExpandableHeightListView
    private lateinit var listApply: ExpandableHeightListView

    private lateinit var upcomingList: List<JobItem>
    private lateinit var applyList: List<JobItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        listUpcoming = binding.root.findViewById(R.id.listUpcoming)
        listApply = binding.root.findViewById(R.id.listApply)

        upcomingList = ArrayList()
        applyList = ArrayList()

        (upcomingList as ArrayList<JobItem>).add(JobItem("Full Home Package", "Apartment Sanitization", "Home Sanitization", "Sanitization", "Aniket Jana", "1/2c, Ballygunge Place East, Ballygunge, Kolkata 700019", "berdyshevyurij@nkgursr.com", "+917001944712", "02 Apr, 2022", "10:15 am"))
        (upcomingList as ArrayList<JobItem>).add(JobItem("Monthly Refresh Cleaning", "Bathroom Cleaninge", "Home Cleaning", "Cleaning Services", "Purnendu Biswas", "157a, Lenin Sarani, Dharmatala, Kolkata 700013", "akaminelinda@emvil.com", "+918001167406", "27 Mar, 2022", "02:00 pm"))
        (upcomingList as ArrayList<JobItem>).add(JobItem("Full Home Essential Package", "Full Home Essential Package", "Home Cleaning", "Cleaning Services", "Dipen Lahiri", "37/a Nirmal Chandra Street, Bowbazar, Kolkata 700012", "justinpog@singmails.com", "+917439657979", "24 Mar, 2022", "9:30 am"))

        val upcomingAdapter = JobAdapter(binding.root.context, upcomingList, JobAction.COMMUNICATION)
        listUpcoming.adapter = upcomingAdapter
        listUpcoming.isExpanded = true

        (applyList as ArrayList<JobItem>).add(JobItem("Full Home Package", "Apartment Sanitization", "Home Sanitization", "Sanitization", "Aniket Jana", "1/2c, Ballygunge Place East, Ballygunge, Kolkata 700019", "berdyshevyurij@nkgursr.com", "+917001944712", "02 Apr, 2022", "10:15 am"))
        (applyList as ArrayList<JobItem>).add(JobItem("Monthly Refresh Cleaning", "Bathroom Cleaninge", "Home Cleaning", "Cleaning Services", "Purnendu Biswas", "157a, Lenin Sarani, Dharmatala, Kolkata 700013", "akaminelinda@emvil.com", "+918001167406", "27 Mar, 2022", "02:00 pm"))
        (applyList as ArrayList<JobItem>).add(JobItem("Full Home Essential Package", "Full Home Essential Package", "Home Cleaning", "Cleaning Services", "Dipen Lahiri", "37/a Nirmal Chandra Street, Bowbazar, Kolkata 700012", "justinpog@singmails.com", "+917439657979", "24 Mar, 2022", "9:30 am"))

        val applyAdapter = JobAdapter(binding.root.context, applyList, JobAction.APPLICATION)
        listApply.adapter = applyAdapter
        listApply.isExpanded = true

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}