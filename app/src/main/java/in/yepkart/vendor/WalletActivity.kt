package `in`.yepkart.vendor

import `in`.yepkart.vendor.adapters.TransactionAdapter
import `in`.yepkart.vendor.controls.ExpandableHeightListView
import `in`.yepkart.vendor.enums.TransactionType
import `in`.yepkart.vendor.models.TransactionItem
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class WalletActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar
    private lateinit var txtBalance: TextView
    private lateinit var listTransactions: ExpandableHeightListView

    private lateinit var transactionList: List<TransactionItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        toolBar = findViewById(R.id.toolbar)
        txtBalance = findViewById(R.id.txtBalance)
        listTransactions = findViewById(R.id.listTransactions)

        setSupportActionBar(toolBar)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        supportActionBar!!.title = "Wallet"
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        transactionList = ArrayList()

        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Money sent to YepKart Admin", "08 March, 2022 12:40 PM", "<b><small>₹</small>2,400</b>", TransactionType.DEBIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Money added to Wallet", "07 March, 2022 9:52 PM", "<b><small>₹</small>2,500</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Gift Voucher xxxxxL899 redeemed", "26 February, 2022 4:32 PM", "<b><small>₹</small>20</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Money sent to YepKart Admin", "11 February, 2022 11:02 AM", "<b><small>₹</small>1,999</b>", TransactionType.DEBIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Money added to Wallet", "11 February, 2022 10:48 AM", "<b><small>₹</small>750</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Money added to Wallet", "08 February, 2022 7:17 PM", "<b><small>₹</small>1,500</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Gift Voucher xxxxxC254 redeemed", "26 January, 2022 6:12 PM", "<b><small>₹</small>12</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Joining Bonus added", "25 January, 2022 2:44 PM", "<b><small>₹</small>100</b>", TransactionType.CREDIT))

        val transactionAdapter = TransactionAdapter(this, transactionList)
        listTransactions.adapter = transactionAdapter
        listTransactions.isExpanded = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtBalance.text = Html.fromHtml("<small>₹</small>483", Html.FROM_HTML_MODE_LEGACY)
        }
        else {
            txtBalance.text = Html.fromHtml("<small>₹</small>483")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            finish()
        }

        return true
    }
}