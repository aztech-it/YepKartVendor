package `in`.yepkart.vendor.adapters

import `in`.yepkart.vendor.DetailsActivity
import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.enums.JobAction
import `in`.yepkart.vendor.models.JobItem
import android.annotation.SuppressLint
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

    @SuppressLint("ViewHolder", "InflateParams", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val jobItem = jobList[position]
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_job, null, false)

        val txtServiceJob = itemView.findViewById<TextView>(R.id.txtServiceJob)
        val txtServiceCategory = itemView.findViewById<TextView>(R.id.txtServiceCategory)
        val txtCustomerName = itemView.findViewById<TextView>(R.id.txtCustomerName)
        val txtCustomerAddress = itemView.findViewById<TextView>(R.id.txtCustomerAddress)
        val txtServiceDate = itemView.findViewById<TextView>(R.id.txtServiceDate)
        val txtServiceTime = itemView.findViewById<TextView>(R.id.txtServiceTime)
        val btnAction1 = itemView.findViewById<Button>(R.id.btnAction1)
        val btnAction2 = itemView.findViewById<Button>(R.id.btnAction2)

        txtServiceJob.text = jobItem.job
        txtServiceCategory.text = jobItem.subCategory
        txtCustomerName.text = jobItem.customerName
        txtCustomerAddress.text = jobItem.customerAddress
        txtServiceDate.text = jobItem.serviceDate
        txtServiceTime.text = jobItem.serviceTime.uppercase()

        when(action) {
            JobAction.APPLICATION -> {
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_apply)

                btnAction1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                btnAction1.setText(R.string.title_apply_now)
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
        }

        btnAction2.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            mContext.startActivity(intent)
        }

        return itemView
    }
}