package `in`.yepkart.vendor

import `in`.yepkart.vendor.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        binding.appBarMain.toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_hamburger)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mDrawerLayout!!.openDrawer(Gravity.START)
        }

        return true
    }
}