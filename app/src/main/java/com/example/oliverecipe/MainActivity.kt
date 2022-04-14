package com.example.oliverecipe

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        //set default screen
        bottom_navigation.selectedItemId = R.id.action_refrigerator
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
            R.id.action_search ->{
                var searchFragment = SearchViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, searchFragment).commit()
                return true
            }
        }
        return false
    }
}