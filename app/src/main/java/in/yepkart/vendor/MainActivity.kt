package `in`.yepkart.vendor

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import android.R.attr.data
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var empList = ArrayList<Employee>()

        var txtEmpName = findViewById<TextView>(R.id.txtEmpName)

        var dbRef: DatabaseReference = Firebase.database.reference

        var query = dbRef.child("employee")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val emp = postSnapshot.getValue(Employee::class.java)!!

                    empList.add(emp)
                }

                txtEmpName.text = empList.size.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



//        query.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                val emp = snapshot.getValue(Employee::class.java)!!
//
//                empList.add(emp)

//                  txtEmpName.text = empList.size.toString()
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

    }
}