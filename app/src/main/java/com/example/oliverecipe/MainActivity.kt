package com.example.oliverecipe

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.oliverecipe.databinding.ActivityMainBinding
import com.example.oliverecipe.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity()
//    , BottomNavigationView.OnNavigationItemSelectedListener
{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.myToolbar)

        //setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val navBottomView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.action_home, R.id.action_refrigerator, R.id.action_foodbank, R.id.action_favorite, R.id.action_shopping_bag
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)

        // bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        //set default screen
//        bottom_navigation.selectedItemId = R.id.action_home
//        bottom_navigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {

            R.id.action_add -> {
                bottom_navigation.selectedItemId = R.id.action_refrigerator

            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun home() {

        var homeViewFragment = HomeViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, homeViewFragment).commit()
    }

//    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
//        toolbar_title_image.visibility = View.VISIBLE
//        when(p0.itemId){
//            R.id.action_refrigerator ->{
//                var refrigeratorViewFragment = RefrigeratorViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, refrigeratorViewFragment).commit()
//                return true
//            }
//            R.id.action_foodbank ->{
//                var foodbankViewFragment = FoodbankViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, foodbankViewFragment).commit()
//                return true
//            }
//            R.id.action_favorite ->{
//                var favoriteFragment = FavoriteViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, favoriteFragment).commit()
//                return true
//            }
//            R.id.action_shopping_bag ->{
//                var bagFragment = BagViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, bagFragment).commit()
//                return true
//            }
//            R.id.action_home -> {
//                home()
//                return true
//            }
//        }
//        return false
//    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}