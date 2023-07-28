package com.food.foodrunnerapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.food.foodrunnerapplication.R

class MainActivity : AppCompatActivity() {


    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences( "Login_Details", Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("isLoggedIn",false) == true){
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainPageActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        } else{
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }

    }

    override fun onPause(){
        super.onPause()
        finish()
    }
}