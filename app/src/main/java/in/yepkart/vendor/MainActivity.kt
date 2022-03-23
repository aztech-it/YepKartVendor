package `in`.yepkart.vendor

import `in`.yepkart.vendor.databinding.ActivityMainBinding
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var mDrawerLayout: DrawerLayout? = null
    private lateinit var btnTask: FloatingActionButton

    private var clickedItem: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)
        btnTask = findViewById(R.id.btnTask)

        binding.appBarMain.toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_hamburger)

        supportActionBar!!.title = "Welcome User"
        supportActionBar!!.subtitle = "Ranaghat, Nadia"

        mDrawerLayout!!.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {
                when (clickedItem) {
                    R.id.nav_profile -> {
                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    R.id.nav_payment -> {
                        val intent = Intent(this@MainActivity, PaymentActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    R.id.nav_wallet -> {
                        val intent = Intent(this@MainActivity, WalletActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    R.id.nav_legal -> {
                        val intent = Intent(this@MainActivity, LegalActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    R.id.nav_about -> {
                        val intent = Intent(this@MainActivity, AboutActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    R.id.nav_tour -> {
                        val intent = Intent(this@MainActivity, TourActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                }

                clickedItem = 0
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })

        val navView = findViewById<NavigationView>(R.id.nav_view)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.app_bar)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation_bar)

        navView.setNavigationItemSelectedListener(this)

        val radius = resources.getDimension(R.dimen.round_10)

        val shapeDrawable : MaterialShapeDrawable= bottomAppBar.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        btnTask.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
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

        clickedItem = item.itemId

        return true
    }
}