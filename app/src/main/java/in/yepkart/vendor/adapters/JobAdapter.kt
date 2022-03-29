package `in`.yepkart.vendor.adapters

import `in`.yepkart.vendor.ApplyActivity
import `in`.yepkart.vendor.DetailsActivity
import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.enums.JobAction
import `in`.yepkart.vendor.models.JobItem
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter


class JobAdapter(private var mContext: Context, private var jobList: List<JobItem>, private var action: JobAction): BaseAdapter() {

    override fun getCount(): Int {
        return jobList.size
    }

    override fun getItem(position: Int): Any {
        return jobList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewHolder", "InflateParams", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val jobItem = jobList[position]
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_job, null, false)

        val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        val txtServiceJob = itemView.findViewById<TextView>(R.id.txtServiceJob)
        val txtServiceCategory = itemView.findViewById<TextView>(R.id.txtServiceCategory)
        val txtCustomerName = itemView.findViewById<TextView>(R.id.txtCustomerName)
        val txtCustomerAddress = itemView.findViewById<TextView>(R.id.txtCustomerAddress)
        val txtServiceDate = itemView.findViewById<TextView>(R.id.txtServiceDate)
        val txtServiceTime = itemView.findViewById<TextView>(R.id.txtServiceTime)
        val btnAction1 = itemView.findViewById<Button>(R.id.btnAction1)
        val btnAction2 = itemView.findViewById<Button>(R.id.btnAction2)

        txtServiceJob.text = jobItem.serviceJob
        txtServiceCategory.text = jobItem.serviceSubcategory
        txtCustomerName.text = jobItem.customerName
        txtCustomerAddress.text = jobItem.customerAddress
        txtServiceDate.text = jobItem.serviceDate.format(dateFormatter).uppercase()
        txtServiceTime.text = jobItem.serviceDate.format(timeFormatter).uppercase()

        when(action) {
            JobAction.APPLICATION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_apply)

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_apply_now)

                btnAction1.setOnClickListener {
                    val intent = Intent(mContext, ApplyActivity::class.java)

                    intent.putExtra("order_id", jobItem.orderId)
                    mContext.startActivity(intent)
                    (mContext as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
            JobAction.COMMUNICATION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_call)

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_call_now)

                btnAction1.setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + jobItem.customerMobile))
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(callIntent)
                }
            }
            JobAction.VERIFICATION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_finish)

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_finish_now)
            }
            JobAction.COMPLETION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_success)
                drawable!!.setTint(mContext.resources.getColor(R.color.colorSuccessDark))

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_finish_now)
                btnAction1.setTextColor(mContext.resources.getColor(R.color.colorSuccessDark))
            }
            JobAction.CANCELLATION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_error)
                drawable!!.setTint(mContext.resources.getColor(R.color.colorDangerDark))

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_finish_now)
                btnAction1.setTextColor(mContext.resources.getColor(R.color.colorDangerDark))
            }
        }

        btnAction2.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)

            intent.putExtra("order_id", jobItem.orderId)
            mContext.startActivity(intent)
            (mContext as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        return itemView
    }
}