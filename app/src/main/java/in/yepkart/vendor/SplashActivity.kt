package `in`.yepkart.vendor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var page = Intent(this, MainActivity::class.java)
        startActivity(page)

        Thread.sleep(2000)
    }
}