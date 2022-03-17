package `in`.yepkart.vendor

import `in`.yepkart.vendor.adapters.TransactionAdapter
import `in`.yepkart.vendor.controls.ExpandableHeightListView
import `in`.yepkart.vendor.enums.TransactionType
import `in`.yepkart.vendor.models.TransactionItem
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar

class PaymentActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar
    private lateinit var listTransactions: ExpandableHeightListView

    private lateinit var transactionList: List<TransactionItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        toolBar = findViewById(R.id.toolbar)
        listTransactions = findViewById(R.id.listTransactions)

        setSupportActionBar(toolBar)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        supportActionBar!!.title = "All Payments"
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        transactionList = ArrayList()

        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Commission received for Job. 6691233", "10 March, 2022 6:32 PM", "<b><small>₹</small>725</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Commission received for Job. 5101329", "22 February, 2022 09:38 PM", "<b><small>₹</small>200</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Commission received for Job. 4050632", "08 February, 2022 7:29 PM", "<b><small>₹</small>485</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Commission received for Job. 3905628", "04 February, 2022 8:02 PM", "<b><small>₹</small>695</b>", TransactionType.CREDIT))
        (transactionList as ArrayList<TransactionItem>).add(TransactionItem("Commission received for Job. 3290541", "30 January, 2022 7:14 PM", "<b><small>₹</small>350</b>", TransactionType.CREDIT))

        val transactionAdapter = TransactionAdapter(this, transactionList)
        listTransactions.adapter = transactionAdapter
        listTransactions.isExpanded = true
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