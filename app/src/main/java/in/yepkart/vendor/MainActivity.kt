package `in`.yepkart.vendor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtEmpName = findViewById<EditText>(R.id.txtEmpName) as EditText

        var id1 = "AZ-52301257"
        var id2 = "AZ-52320004"

        var emp1 = Employee("Anirban Debroy", "9999999999", "example1@abcd.com", "FBVHT2522S", "", 8000f, true)
        var emp2 = Employee("Subhajit Chakraborty", "8888888888", "example2@abcd.com", "YGFDG9820S", "", 8000f, true)

        var dbRef: DatabaseReference = Firebase.database.reference

//        dbRef.child("employee").child(id1).setValue(emp1)
//        dbRef.child("employee").child(id2).setValue(emp2)

        var empList = ArrayList<Employee>()

        dbRef.child("employee").child(id2).get().addOnCompleteListener {
            if(it.isSuccessful) {
                val result = it.result

                val name: String = result!!.child("email")?.child("1").getValue<String>().toString()

                var emp = Employee(name,"","","","",0f,true)

                empList.add(emp)

                txtEmpName.setText(name)
            }
        }

    }
}