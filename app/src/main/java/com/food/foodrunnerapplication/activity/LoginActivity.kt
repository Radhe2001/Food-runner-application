package com.food.foodrunnerapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.food.foodrunnerapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    lateinit var mobile : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var forgot : TextView
    lateinit var register : TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgot = findViewById(R.id.txtForgot)
        login = findViewById(R.id.btnLogin)
        register = findViewById(R.id.txtRegister)
        password = findViewById(R.id.etPassword)
        mobile = findViewById(R.id.etMobile)

        //creating shared preference object
        sharedPreferences = getSharedPreferences( "Login_Details", Context.MODE_PRIVATE)

        // using this editor we can edit the values stored in the shared preferences
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        //for moving to the main page
        login.setOnClickListener{

            //it will save the mobile number and password to the mobileNumber and pswd variable respectively
            var mobileNumber: String = mobile.text.toString()
            var pswd : String = password.text.toString()

            val database:DatabaseReference=FirebaseDatabase.getInstance().getReference("user")
            database.child(mobileNumber).get().addOnSuccessListener {
                val password = it.child("password").value as String

                if(pswd == password){

                    editor.putBoolean("isLoggedIn",true).apply()
                    editor.putString("mobile",mobileNumber).apply()
                    val intent = Intent(this@LoginActivity , MainPageActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this@LoginActivity,"Invalid Credentials",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this@LoginActivity,"Failed to login",Toast.LENGTH_LONG).show()
            }


        }
        //for getting forwarded to registration activity
        register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterationActivity::class.java)
            startActivity(intent)
        }
        //for getting forwarded to forgot password page
        forgot.setOnClickListener{
            val intent = Intent(this@LoginActivity , FogotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause(){
        super.onPause()
        finish()
    }
}