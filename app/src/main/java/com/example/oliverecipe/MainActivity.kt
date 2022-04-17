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
import androidx.navigation.ui.AppBarConfiguration
import com.example.oliverecipe.databinding.ActivityMainBinding
import com.example.oliverecipe.navigation.HomeViewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        //set default screen
        bottom_navigation.selectedItemId = R.id.action_home




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                bottom_navigation.selectedItemId = R.id.action_home
            }

            R.id.menu_add_camera -> {
                bottom_navigation.selectedItemId = R.id.action_refrigerator
                // 추가

            }

            R.id.menu_add_gallary -> {
                bottom_navigation.selectedItemId = R.id.action_refrigerator
                // 추가

            }

            R.id.action_info -> {
                Toast.makeText(this,"버전 정보", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_setting -> {
                Toast.makeText(this,"설정", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        toolbar_title_image.visibility = View.VISIBLE
        when(p0.itemId){
            R.id.action_refrigerator ->{
                var refrigeratorViewFragment = RefrigeratorViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, refrigeratorViewFragment).commit()
                return true
            }
            R.id.action_foodbank ->{
                var foodbankViewFragment = FoodbankViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, foodbankViewFragment).commit()
                return true
            }
            R.id.action_favorite ->{
                var favoriteFragment = FavoriteViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, favoriteFragment).commit()
                return true
            }
            R.id.action_shopping_bag ->{
                var bagFragment = BagViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, bagFragment).commit()
                return true
            }
            R.id.action_home -> {
                var homeViewFragment = HomeViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, homeViewFragment).commit()
                return true
            }
        }
        return false
    }
}