package `in`.yepkart.vendor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar

    private var genderList = ArrayList<String>()
    private var pincodeList = ArrayList<String>()
    private var serviceList = ArrayList<String>()

    private lateinit var txtFirstName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtGender: AutoCompleteTextView
    private lateinit var txtMobile: EditText
    private lateinit var txtAddress: EditText
    private lateinit var txtPincode: AutoCompleteTextView
    private lateinit var groupPincode: ChipGroup
    private lateinit var txtService: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolBar = findViewById(R.id.toolbar)

        setSupportActionBar(toolBar)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        supportActionBar!!.title = "User's Profile"
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        txtFirstName = findViewById(R.id.txtFirstName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtGender = findViewById(R.id.txtGender)
        txtMobile = findViewById(R.id.txtMobile)
        txtAddress = findViewById(R.id.txtAddress)
        txtPincode = findViewById(R.id.txtPincode)
        groupPincode = findViewById(R.id.groupPincode)
        txtService = findViewById(R.id.txtService)

        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Other")

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)
        txtGender.setAdapter(genderAdapter)

        val db = Firebase.database.reference
        val query1 = db.child("settings").child("pincodes")

        query1.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pincodeList = dataSnapshot.getValue(object : GenericTypeIndicator<ArrayList<String>>() {})!!

                val pincodeAdapter = ArrayAdapter(this@ProfileActivity, android.R.layout.simple_dropdown_item_1line, pincodeList)
                txtPincode.setAdapter(pincodeAdapter)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        txtPincode.setOnItemClickListener { adapterView, _, _, _ ->
            var chip = Chip(this)

            chip.text = adapterView.selectedItem.toString()
            chip.isCloseIconVisible = true

            groupPincode.addView(chip)

            txtPincode.setText("")
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