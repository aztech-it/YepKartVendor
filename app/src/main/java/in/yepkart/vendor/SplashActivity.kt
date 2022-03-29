package `in`.yepkart.vendor

import `in`.yepkart.vendor.datasource.DataHelper
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import androidx.annotation.RequiresApi
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

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        dbHelper = DataHelper(this)

        checkRunTimePermission()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var isPermitted = false

        if (requestCode == 49) {
            for (i in grantResults.indices) {
                val permission = permissions[i]
                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    val showRationale = shouldShowRequestPermissionRationale(permission!!)
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    }
                    else {
                        /*if (openDialogOnce) {
                            alertView()
                        }*/
                    }
                }
            }
            if (isPermitted) {
                /*val dataTask = LoadData(this)
                dataTask.execute()*/

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkRunTimePermission() {
        val permissionArrays = arrayOf(Manifest.permission.CALL_PHONE)
        requestPermissions(permissionArrays, 49)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class LoadData(mContext: Context) : AsyncTask<String?, Int?, Void?>() {
        var mContext = mContext
        var dbHelper = DataHelper(mContext)

        override fun doInBackground(vararg obj: String?): Void? {
            val firestore = Firebase.firestore
            val database = Firebase.database.reference

            val filterItem = HashMap<String, String>()

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

                        dbHelper.setJobs(
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
                        )
                    }
                }

            database.child("vendors").orderByChild("mobile").equalTo("+918910125384").addChildEventListener(object:
                ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val services = snapshot.child("services").value as ArrayList<*>

                    for (service in services) {
                        firestore.collection("order").whereEqualTo("service_subcategory", service.toString()).limit(5).get()
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

                                    dbHelper.setJobs(
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
                                    )
                                }
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

            return null
        }

        override fun onPostExecute(result: Void?) {
            val intent = Intent(mContext, MainActivity::class.java)
            mContext.startActivity(intent)
        }
    }
}