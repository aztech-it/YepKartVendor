package `in`.yepkart.vendor

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
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
    private lateinit var groupService: ChipGroup
    private lateinit var txtPanNo: EditText
    private lateinit var txtPanName: EditText
    private lateinit var txtPanDob: EditText
    private lateinit var imgPanCard: ImageView
    private lateinit var txtAadharNo: EditText
    private lateinit var txtAadharAddress: EditText
    private lateinit var imgAadharFront: ImageView
    private lateinit var imgAadharBack: ImageView

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
        groupService = findViewById(R.id.groupService)
        txtPanNo = findViewById(R.id.txtPanNo)
        txtPanName = findViewById(R.id.txtPanName)
        txtPanDob = findViewById(R.id.txtPanDob)
        imgPanCard = findViewById(R.id.imgPanCard)
        txtAadharNo = findViewById(R.id.txtAadharNo)
        txtAadharAddress = findViewById(R.id.txtAadharAddress)
        imgAadharFront = findViewById(R.id.imgAadharFront)
        imgAadharBack = findViewById(R.id.imgAadharBack)

        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Other")

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)
        txtGender.setAdapter(genderAdapter)

        val db = Firebase.database.reference
        val query1 = db.child("settings").child("pincodes")
        val query2 = db.child("category")

        query1.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pincodeList = dataSnapshot.getValue(object : GenericTypeIndicator<ArrayList<String>>() {})!!

                val pincodeAdapter = ArrayAdapter(this@ProfileActivity, android.R.layout.simple_dropdown_item_1line, pincodeList)
                txtPincode.setAdapter(pincodeAdapter)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        query2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (category in snapshot.children) {
                    for (subcategory in category.child("subs").children) {
                        serviceList.add(subcategory.child("display").value.toString())
                    }
                }

                val serviceAdapter = ArrayAdapter(this@ProfileActivity, android.R.layout.simple_dropdown_item_1line, serviceList)
                txtService.setAdapter(serviceAdapter)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        txtPincode.setOnItemClickListener { _, _, position, _ ->
            val chip = Chip(this)

            chip.text = txtPincode.adapter.getItem(position).toString()
            chip.isCloseIconVisible = true
            chip.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

            groupPincode.addView(chip)

            txtPincode.setText("")
        }

        txtService.setOnItemClickListener { _, _, position, _ ->
            val chip = Chip(this)

            chip.text = txtService.adapter.getItem(position).toString()
            chip.isCloseIconVisible = true
            chip.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

            groupService.addView(chip)

            txtService.setText("")
        }

        txtFirstName.setText("Abhrajit")
        txtLastName.setText("Kundu")
        txtEmail.setText("abhrajit06it@gmail.com")
        txtGender.setText("Male")
        txtMobile.setText("7001944712")
        txtAddress.setText("59/1 Mission Road, Ranaghat, Nadia, WB - 741201")

        val pinChip1 = Chip(this)

        pinChip1.text = "700012"
        pinChip1.isCloseIconVisible = true
        pinChip1.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val pinChip2 = Chip(this)

        pinChip2.text = "700015"
        pinChip2.isCloseIconVisible = true
        pinChip2.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val pinChip3 = Chip(this)

        pinChip3.text = "700017"
        pinChip3.isCloseIconVisible = true
        pinChip3.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val pinChip4 = Chip(this)

        pinChip4.text = "700018"
        pinChip4.isCloseIconVisible = true
        pinChip4.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val pinChip5 = Chip(this)

        pinChip5.text = "700021"
        pinChip5.isCloseIconVisible = true
        pinChip5.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        groupPincode.addView(pinChip1)
        groupPincode.addView(pinChip2)
        groupPincode.addView(pinChip3)
        groupPincode.addView(pinChip4)
        groupPincode.addView(pinChip5)


        val serviceChip1 = Chip(this)

        serviceChip1.text = "Home Cleaning"
        serviceChip1.isCloseIconVisible = true
        serviceChip1.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val serviceChip2 = Chip(this)

        serviceChip2.text = "Home Sanitization"
        serviceChip2.isCloseIconVisible = true
        serviceChip2.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        val serviceChip3 = Chip(this)

        serviceChip3.text = "Commercial Space Sanitization"
        serviceChip3.isCloseIconVisible = true
        serviceChip3.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.montserrat_medium), Typeface.NORMAL)

        groupService.addView(serviceChip1)
        groupService.addView(serviceChip2)
        groupService.addView(serviceChip3)

        txtPanNo.setText("ABCDE1234G")
        txtPanName.setText("ABHRAJIT KUNDU")
        txtPanDob.setText("14-02-1986")
        imgPanCard.setImageResource(R.drawable.sample_pan)
        txtAadharNo.setText("523618006315")
        txtAadharAddress.setText("59/1 Mission Road, Word - 12, P.O. Ranaghat, Nadia, 741201")
        imgAadharFront.setImageResource(R.drawable.sample_aadhar_1)
        imgAadharBack.setImageResource(R.drawable.sample_aadhar_2)
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