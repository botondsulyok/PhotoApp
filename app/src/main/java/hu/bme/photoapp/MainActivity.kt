package hu.bme.photoapp

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar_main) as Toolbar
        setSupportActionBar(toolbar)

        drawer = drawer_layout

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_white_24)

        navigationView = nav_view
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_nav_home_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_categories -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_nav_categories_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_competitions -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_competitions_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_logout -> {
                MainActivityViewModel.user = User("")
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_logout)
                drawer.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
