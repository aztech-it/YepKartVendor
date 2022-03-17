package `in`.yepkart.vendor.adapters

import `in`.yepkart.vendor.models.TransactionItem
import `in`.yepkart.vendor.R
import `in`.yepkart.vendor.enums.TransactionType
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TransactionAdapter(private var mContext: Context, private var transactionList: List<TransactionItem>): BaseAdapter() {

    override fun getCount(): Int {
        return transactionList.size
    }

    override fun getItem(position: Int): Any {
        return transactionList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val transactionItem = transactionList[position]
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_transaction, null, false)

        val txtRemarks = itemView.findViewById<TextView>(R.id.txtRemarks)
        val txtTimeStamp = itemView.findViewById<TextView>(R.id.txtTimeStamp)
        val txtAmount = itemView.findViewById<TextView>(R.id.txtAmount)

        txtRemarks.text = transactionItem.remarks
        txtTimeStamp.text = transactionItem.timeStamp

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAmount.text = Html.fromHtml(transactionItem.amount, Html.FROM_HTML_MODE_LEGACY)
        }
        else {
            txtAmount.text = Html.fromHtml(transactionItem.amount)
        }

        when(transactionItem.type) {
            TransactionType.CREDIT -> txtAmount.setTextColor(mContext.getColor(R.color.colorSuccessDark))
            else -> txtAmount.setTextColor(mContext.getColor(R.color.colorDangerDark))
        }

        return itemView
    }
}