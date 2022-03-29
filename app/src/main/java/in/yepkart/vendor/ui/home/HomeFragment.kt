package `in`.yepkart.vendor.ui.home

import `in`.yepkart.vendor.ListActivity
import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.adapters.JobAdapter
import `in`.yepkart.vendor.controls.ExpandableHeightListView
import `in`.yepkart.vendor.enums.JobAction
import `in`.yepkart.vendor.models.JobItem
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import io.supercharge.shimmerlayout.ShimmerLayout

class HomeFragment : Fragment() {

    private lateinit var layoutRefresh: SwipeRefreshLayout
    private lateinit var listUpcoming: ExpandableHeightListView
    private lateinit var listApply: ExpandableHeightListView
    private lateinit var shimmerLayout1: ShimmerLayout
    private lateinit var shimmerLayout2: ShimmerLayout
    private lateinit var btnViewUpcoming: Button
    private lateinit var btnViewApply: Button

    private lateinit var upcomingAdapter: JobAdapter
    private lateinit var applyAdapter: JobAdapter

    private lateinit var upcomingList: List<JobItem>
    private lateinit var applyList: List<JobItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        layoutRefresh = view.findViewById(R.id.layout_refresh)
        listUpcoming = view.findViewById(R.id.listUpcoming)
        listApply = view.findViewById(R.id.listApply)
        shimmerLayout1 = view.findViewById(R.id.skeletonUpcoming)
        shimmerLayout2 = view.findViewById(R.id.skeletonApply)
        btnViewUpcoming = view.findViewById(R.id.btnViewUpcoming)
        btnViewApply = view.findViewById(R.id.btnViewApply)

        upcomingList = ArrayList()
        applyList = ArrayList()

        upcomingAdapter = JobAdapter(view.context, upcomingList, JobAction.COMMUNICATION)
        listUpcoming.adapter = upcomingAdapter
        listUpcoming.isExpanded = true

        applyAdapter = JobAdapter(view.context, applyList, JobAction.APPLICATION)
        listApply.adapter = applyAdapter
        listApply.isExpanded = true

        loadData()

        layoutRefresh.setOnRefreshListener {
            loadData()
            layoutRefresh.isRefreshing = false
        }

        btnViewUpcoming.setOnClickListener {
            val intent = Intent(view.context, ListActivity::class.java)

            intent.putExtra("type", "COMMUNICATION")
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        btnViewApply.setOnClickListener {
            val intent = Intent(view.context, ListActivity::class.java)

            intent.putExtra("type", "APPLICATION")
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        return view
    }

    private fun showUpcomingSkeleton(show: Boolean) {
        if (show) {
            shimmerLayout1.startShimmerAnimation()
            shimmerLayout1.visibility = View.VISIBLE
            listUpcoming.visibility = View.GONE
        }
        else {
            shimmerLayout1.stopShimmerAnimation()
            shimmerLayout1.visibility = View.GONE
            listUpcoming.visibility = View.VISIBLE
        }
    }

    private fun showApplySkeleton(show: Boolean) {
        if (show) {
            shimmerLayout2.startShimmerAnimation()
            shimmerLayout2.visibility = View.VISIBLE
            listApply.visibility = View.GONE
        }
        else {
            shimmerLayout2.stopShimmerAnimation()
            shimmerLayout2.visibility = View.GONE
            listApply.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData() {
        showUpcomingSkeleton(true)
        showApplySkeleton(true)
        val firestore = Firebase.firestore
        val database = Firebase.database.reference

        val filterItem = HashMap<String, String>()

        (upcomingList as ArrayList<JobItem>).clear()
        (applyList as ArrayList<JobItem>).clear()

        filterItem["vendor_email"] = ""
        filterItem["vendor_mobile"] = "+918910125384"
        filterItem["vendor_name"] = "Prabir Saha"

        firestore.collection("order").whereArrayContains("vendor", filterItem).limit(5).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val customer = document.data.getValue("customer") as Map<*, *>

                    val dateString1 = document.data.getValue("order_date").toString()
                    val timeString1 = document.data.getValue("order_time").toString().uppercase()
                    val dateArray1 = dateString1.split("/")

                    val day1 = dateArray1[0].toInt()
                    val month1 = dateArray1[1].toInt()
                    val year1 = dateArray1[2].toInt()

                    val date1 = LocalDate.of(year1, month1, day1)
                    val time1 = LocalTime.parse(timeString1, DateTimeFormatter.ofPattern("hh:mm a"))
                    val dateTime1 = LocalDateTime.of(date1, time1)

                    val dateString2 = document.data.getValue("service_date").toString()
                    val timeString2 = document.data.getValue("service_time").toString().uppercase()
                    val dateArray2 = dateString2.split("/")

                    val day2 = dateArray2[0].toInt()
                    val month2 = dateArray2[1].toInt()
                    val year2 = dateArray2[2].toInt()

                    val date2 = LocalDate.of(year2, month2, day2)
                    val time2 = LocalTime.parse(timeString2, DateTimeFormatter.ofPattern("hh:mm a"))
                    val dateTime2 = LocalDateTime.of(date2, time2)

                    (upcomingList as ArrayList<JobItem>).add(
                        JobItem(
                            document.id,
                            document.data.getValue("order_amount").toString().toFloat(),
                            dateTime1,
                            document.data.getValue("order_status").toString(),
                            document.data.getValue("payment_mode").toString(),
                            document.data.getValue("payment_status").toString(),
                            dateTime2,
                            document.data.getValue("service_category").toString(),
                            document.data.getValue("service_subcategory").toString(),
                            document.data.getValue("service_name").toString(),
                            document.data.getValue("service_job").toString(),
                            "",
                            customer["customer_name"].toString(),
                            customer["customer_mobile"].toString(),
                            customer["customer_email"].toString(),
                            customer["customer_address"].toString()
                    ))
                }

                upcomingAdapter.notifyDataSetChanged()
                showUpcomingSkeleton(false)
            }


        database.child("vendors").orderByChild("mobile").equalTo("+918910125384").addChildEventListener(object:
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val services = snapshot.child("services").value as ArrayList<*>

                for (service in services) {
                    firestore.collection("order").whereEqualTo("service_subcategory", service.toString()).limit(5).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                if ((document.data.getValue("vendor") as ArrayList<*>).size <= 0) {
                                    val customer = document.data.getValue("customer") as Map<*, *>

                                    val dateString1 = document.data.getValue("order_date").toString()
                                    val timeString1 = document.data.getValue("order_time").toString().uppercase()
                                    val dateArray1 = dateString1.split("/")

                                    val day1 = dateArray1[0].toInt()
                                    val month1 = dateArray1[1].toInt()
                                    val year1 = dateArray1[2].toInt()

                                    val date1 = LocalDate.of(year1, month1, day1)
                                    val time1 = LocalTime.parse(timeString1, DateTimeFormatter.ofPattern("hh:mm a"))
                                    val dateTime1 = LocalDateTime.of(date1, time1)

                                    val dateString2 = document.data.getValue("service_date").toString()
                                    val timeString2 = document.data.getValue("service_time").toString().uppercase()
                                    val dateArray2 = dateString2.split("/")

                                    val day2 = dateArray2[0].toInt()
                                    val month2 = dateArray2[1].toInt()
                                    val year2 = dateArray2[2].toInt()

                                    val date2 = LocalDate.of(year2, month2, day2)
                                    val time2 = LocalTime.parse(timeString2, DateTimeFormatter.ofPattern("hh:mm a"))
                                    val dateTime2 = LocalDateTime.of(date2, time2)

                                    (applyList as ArrayList<JobItem>).add(
                                        JobItem(
                                            document.id,
                                            document.data.getValue("order_amount").toString().toFloat(),
                                            dateTime1,
                                            document.data.getValue("order_status").toString(),
                                            document.data.getValue("payment_mode").toString(),
                                            document.data.getValue("payment_status").toString(),
                                            dateTime2,
                                            document.data.getValue("service_category").toString(),
                                            document.data.getValue("service_subcategory").toString(),
                                            document.data.getValue("service_name").toString(),
                                            document.data.getValue("service_job").toString(),
                                            "",
                                            customer["customer_name"].toString(),
                                            customer["customer_mobile"].toString(),
                                            customer["customer_email"].toString(),
                                            customer["customer_address"].toString()
                                        ))
                                }
                            }

                            applyAdapter.notifyDataSetChanged()
                            showApplySkeleton(false)
                        }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}