package `in`.yepkart.vendor

import `in`.yepkart.vendor.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        binding.appBarMain.toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_hamburger)

        supportActionBar!!.title = "Welcome User"
        supportActionBar!!.subtitle = "Administrator"

        val navView = findViewById<NavigationView>(R.id.nav_view)

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawerLayout!!.closeDrawer(GravityCompat.START)

        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_gallery -> {
                Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_slideshow -> {
                Toast.makeText(this, "Slideshow", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }
}