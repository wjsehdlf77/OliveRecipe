package com.example.oliverecipe

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.oliverecipe.navigation.API.Row
import com.example.oliverecipe.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity()

{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.appBarMain.myToolbar
        setSupportActionBar(toolbar)

        //setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val navBottomView: BottomNavigationView = findViewById(R.id.bottom_navigation)

//        val drawerToggle = ActionBarDrawerToggle(
//            MainActivity(),
//            drawerLayout,
//            toolbar,
//            R.string.app_name,
//            R.string.app_name)
//        drawerToggle.syncState()


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.action_home,
                R.id.action_refrigerator,
                R.id.action_foodbank,
                R.id.action_favorite,
                R.id.action_shopping_bag,
                R.id.action_add,
                R.id.action_market
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)


        // bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        ) }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHost = findNavController(R.id.nav_host_fragment_content_main)
        when (item?.itemId) {

            R.id.action_menu_add -> {
                navHost.navigate(R.id.action_add)
                return true

//                var addFoodViewFragment = AddFoodViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, addFoodViewFragment).commit()
//                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}