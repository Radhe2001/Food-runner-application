package com.food.foodrunnerapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.food.foodrunnerapplication.R
import com.food.foodrunnerapplication.UserDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterationActivity : AppCompatActivity() {

    lateinit var back : ImageView
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var mobile : EditText
    lateinit var address : EditText
    lateinit var password : EditText
    lateinit var confirmPassword : EditText
    lateinit var register : Button
    lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        back = findViewById(R.id.imgBackIcon)
        name = findViewById(R.id.etNameRegister)
        email = findViewById(R.id.etEmailAddressRegister)
        mobile = findViewById(R.id.etMoblieRegister)
        address = findViewById(R.id.etDeliveryAddressRegister)
        password = findViewById(R.id.etPasswordRegister)
        confirmPassword = findViewById(R.id.etConfirmPasswordRegister)
        register = findViewById(R.id.btnRegister)

        register.setOnClickListener {

            if(password.text.toString() == confirmPassword.text.toString()){

                val nameU = name.text.toString()
                val emailU =  email.text.toString()
                val mobileU =mobile.text.toString()
                val addressU = address.text.toString()
                val passwordU =  password.text.toString()

                database = FirebaseDatabase.getInstance().getReference("user")
                val user = UserDetails(nameU,emailU,mobileU,addressU,passwordU)

                database.child(mobileU).setValue(user).addOnSuccessListener {
                    Toast.makeText(this@RegisterationActivity,"Successfully registered",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegisterationActivity , LoginActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener{
                    Toast.makeText(this@RegisterationActivity,"Failed to register",Toast.LENGTH_LONG).show()
                }


            } else {
                Toast.makeText(this@RegisterationActivity,"Password not matched",Toast.LENGTH_LONG).show()
            }

        }

        back.setOnClickListener {
            val intent = Intent(this@RegisterationActivity , LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause(){
        super.onPause()
        finish()
    }
}