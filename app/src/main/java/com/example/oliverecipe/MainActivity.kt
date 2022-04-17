package com.example.oliverecipe

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.oliverecipe.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

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
        bottom_navigation.selectedItemId = R.id.action_refrigerator


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                var refrigeratorViewFragment = RefrigeratorViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, refrigeratorViewFragment).commit()
                return true
            }

            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                Toast.makeText(this,"문자 보내기", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_email -> {
                Toast.makeText(this,"이메일 보내기", Toast.LENGTH_SHORT).show()
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
            R.id.action_search ->{
                var searchFragment = SearchViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, searchFragment).commit()
                return true
            }
        }
        return false
    }
}