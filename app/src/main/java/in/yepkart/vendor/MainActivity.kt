package `in`.yepkart.vendor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import `in`.yepkart.vendor.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        binding.appBarMain.toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_hamburger)

        supportActionBar!!.title = "Welcome User"
        supportActionBar!!.subtitle = "Ranaghat, Nadia"

        val navView = findViewById<NavigationView>(R.id.nav_view)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.app_bar)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation_bar)

        navView.setNavigationItemSelectedListener(this)

        val radius = resources.getDimension(R.dimen.round_20)

        val shapeDrawable : MaterialShapeDrawable= bottomAppBar.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
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