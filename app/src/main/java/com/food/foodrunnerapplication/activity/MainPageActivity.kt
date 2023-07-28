package com.food.foodrunnerapplication.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Display.Mode
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
//import android.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.food.foodrunnerapplication.R
import com.food.foodrunnerapplication.fragment.AboutAppFragment
import com.food.foodrunnerapplication.fragment.DashboardFragment
import com.food.foodrunnerapplication.fragment.FavouriteFragment
import com.food.foodrunnerapplication.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainPageActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem : MenuItem? = null
    lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        drawerLayout = findViewById(R.id.drawerLayoutMainPage)
        coordinatorLayout = findViewById(R.id.coordinatorLayoutMainPage)
        toolbar = findViewById(R.id.toolbarMainLayout)
        frameLayout = findViewById(R.id.frameLayourMainPage)
        navigationView = findViewById(R.id.navigationLayoutMainPage)

        setUpToolbar()

        /*// to open the dashboard as first screen
        supportFragmentManager.beginTransaction().replace(R.id.frameLayourMainPage,DashboardFragment())
            .addToBackStack("Dashboard").commit()
        supportActionBar?.title = "Dashboard"*/

        //alternative function call for the same as above
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainPageActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }

                previousMenuItem?.isChecked = false
                it.isCheckable = true
                it.isChecked = true
                previousMenuItem = it

            when(it.itemId){
                R.id.itmDashboard -> {

                    openDashboard()
                    drawerLayout.closeDrawers()
                    //first method to implement
                    /*//this will take the user to the desired screen but the drawer will remain open
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayourMainPage,DashboardFragment())
                        .addToBackStack("Dashboard").commit()
                    //to add the title to the each of the fragments differently
                    supportActionBar?.title = "Dashboard"
                    //to close the drawer we use this method
                    drawerLayout.closeDrawers()*/

                    //rather we can also make the function for the same and call the function

                }
                R.id.itmFavourites -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayourMainPage,
                        FavouriteFragment()
                    ).commit()

                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.itmProfile -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayourMainPage,
                        ProfileFragment()
                    ).commit()

                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.itmLogout -> {
//                    supportFragmentManager.beginTransaction().replace(
//                        R.id.frameLayourMainPage,
//                        AboutAppFragment()
//                    ).commit()
//
//                    supportActionBar?.title = "About App"
//                    drawerLayout.closeDrawers()
                    sharedPreferences = getSharedPreferences("Login_Details", MODE_PRIVATE)
                    val intent = Intent(this@MainPageActivity,LoginActivity::class.java)
                    val editor:Editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn",false).apply()
                    startActivity(intent)
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayourMainPage,
            DashboardFragment()
        ).commit()
        supportActionBar?.title = "Dashboard"

        navigationView.setCheckedItem(R.id.itmDashboard)
        //alternative way to define the same function
        /*
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment
        transaction.addToBackStack("Dashboard")
        transaction.commit()

        supportActionBar?.title = "Dashboard"
         */
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayourMainPage)
        when(frag){
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}