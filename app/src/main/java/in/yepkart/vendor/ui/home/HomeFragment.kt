package `in`.yepkart.vendor.ui.home

import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.adapters.JobAdapter
import `in`.yepkart.vendor.controls.ExpandableHeightListView
import `in`.yepkart.vendor.enums.JobAction
import `in`.yepkart.vendor.models.JobItem
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HomeFragment : Fragment() {

    private lateinit var layoutRefresh: SwipeRefreshLayout
    private lateinit var listUpcoming: ExpandableHeightListView
    private lateinit var listApply: ExpandableHeightListView

    private lateinit var upcomingList: List<JobItem>
    private lateinit var applyList: List<JobItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        layoutRefresh = view.findViewById(R.id.layout_refresh)
        listUpcoming = view.findViewById(R.id.listUpcoming)
        listApply = view.findViewById(R.id.listApply)

        upcomingList = ArrayList()
        applyList = ArrayList()

        loadCommunicationData(view.context)
        loadApplicationData(view.context)

        layoutRefresh.setOnRefreshListener {
            loadCommunicationData(view.context)
            loadApplicationData(view.context)
            layoutRefresh.isRefreshing = false
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadCommunicationData(mContext: Context) {
        val firestore = Firebase.firestore

        (upcomingList as ArrayList<JobItem>).clear()

        firestore.collection("order").whereEqualTo("vender.vender_mobile", "+917980303174").limit(5).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val customer = document.data.getValue("customer") as Map<*, *>

                    val dateString = document.data.getValue("service_date").toString()
                    val dateArray = dateString.split("/")

                    val day = dateArray[0].toInt()
                    val month = dateArray[1].toInt()
                    val year = dateArray[2].toInt()

                    val date = LocalDate.of(year, month, day)

                    (upcomingList as ArrayList<JobItem>).add(
                        JobItem(
                            document.data.getValue("service_job").toString(),
                            document.data.getValue("service_name").toString(),
                            document.data.getValue("service_subcategory").toString(),
                            document.data.getValue("service_category").toString(),
                            customer["customer_name"].toString(),
                            customer["customer_address"].toString(),
                            customer["customer_email"].toString(),
                            customer["customer_mobile"].toString(),
                            date,
                            document.data.getValue("service_time").toString()
                        )
                    )
                }

                (upcomingList as ArrayList<JobItem>).sortWith(compareBy<JobItem> { it.serviceDate.year }.thenBy { it.serviceDate.month }.thenBy { it.serviceDate.dayOfMonth })

                val upcomingAdapter = JobAdapter(mContext, upcomingList, JobAction.COMMUNICATION)
                listUpcoming.adapter = upcomingAdapter
                listUpcoming.isExpanded = true
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadApplicationData(mContext: Context) {
        val database = Firebase.database.reference
        val firestore = Firebase.firestore

        (applyList as ArrayList<JobItem>).clear()

        database.child("vendors").orderByChild("mobile").equalTo("+917980303174").addChildEventListener(object:
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val services = snapshot.child("services").value as ArrayList<*>

                for (service in services) {
                    firestore.collection("order").whereEqualTo("service_subcategory", service.toString()).limit(5).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val customer = document.data.getValue("customer") as Map<*, *>

                                val dateString = document.data.getValue("service_date").toString()
                                val dateArray = dateString.split("/")

                                val day = dateArray[0].toInt()
                                val month = dateArray[1].toInt()
                                val year = dateArray[2].toInt()

                                val date = LocalDate.of(year, month, day)

                                (applyList as ArrayList<JobItem>).add(
                                    JobItem(
                                        document.data.getValue("service_job").toString(),
                                        document.data.getValue("service_name").toString(),
                                        document.data.getValue("service_subcategory").toString(),
                                        document.data.getValue("service_category").toString(),
                                        customer["customer_name"].toString(),
                                        customer["customer_address"].toString(),
                                        customer["customer_email"].toString(),
                                        customer["customer_mobile"].toString(),
                                        date,
                                        document.data.getValue("service_time").toString()
                                    )
                                )
                            }

                            (applyList as ArrayList<JobItem>).sortWith(compareBy<JobItem> { it.serviceDate.year }.thenByDescending { it.serviceDate.month }.thenByDescending { it.serviceDate.dayOfMonth })

                            val applyAdapter = JobAdapter(mContext, applyList, JobAction.APPLICATION)
                            listApply.adapter = applyAdapter
                            listApply.isExpanded = true
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