package `in`.yepkart.vendor

import android.Manifest
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {

    private val permissions = arrayOfNulls<String>(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        permissions[0] = Manifest.permission.CALL_PHONE

        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(this@SplashActivity, permissions, 49)
        }
        else {
            val page = Intent(this, MainActivity::class.java)
            startActivity(page)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 49) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]

                if (permission == Manifest.permission.CALL_PHONE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        val page = Intent(this, MainActivity::class.java)
                        startActivity(page)
                    }
                    else {
                        ActivityCompat.requestPermissions(this@SplashActivity, permissions, 49)
                    }
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        for (element in permissions) {
            if (ContextCompat.checkSelfPermission(this@SplashActivity, element!!) != PackageManager.PERMISSION_GRANTED)
                return false
        }

        return true
    }
}