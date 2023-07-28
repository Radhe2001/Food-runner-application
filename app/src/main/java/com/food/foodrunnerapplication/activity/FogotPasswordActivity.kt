package com.food.foodrunnerapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.food.foodrunnerapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FogotPasswordActivity : AppCompatActivity() {

    lateinit var mobileView : EditText
    lateinit var emailView : EditText
    lateinit var btn : Button
    lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fogot_password)

        mobileView = findViewById(R.id.etRegisteredMobile)
        emailView = findViewById(R.id.etRegisteredEmail)
        btn = findViewById(R.id.btnNextForgot)


        btn.setOnClickListener{

            val mobile = mobileView.text.toString()
            val email = emailView.text.toString()
            if(email.isNotEmpty()&&mobile.isNotEmpty()){
                database = FirebaseDatabase.getInstance().getReference("user")
                database.child(mobile).get().addOnSuccessListener {
                    val dbMobile = it.child("mobileNumber").value as String
                    val dbEmail = it.child("email").value as String

                    if(mobile == dbMobile && email == dbEmail){
                        val intent = Intent(this@FogotPasswordActivity,resetPasswordActivity::class.java)
                        intent.putExtra("mobile",dbMobile)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this,"No such user exists",Toast.LENGTH_SHORT).show()
                    }

                }
            } else{
                Toast.makeText(this,"Fill all the fields first",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onPause(){
        super.onPause()
        finish()
    }
}