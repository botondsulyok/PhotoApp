package hu.bme.photoapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import hu.bme.photoapp.MainActivity.Companion.PERMISSION_REQUEST_CODE
import hu.bme.photoapp.upload.UploadFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

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

        val permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        when (permissionResult) {
            PackageManager.PERMISSION_DENIED -> requestNeededPermission()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_nav_home_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_categories -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_nav_categories_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_competitions -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_competitions_clicked)
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_logout -> {
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

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this,
                    "I need it for camera", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CAMERA permission granted",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "CAMERA permission NOT granted",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
