package com.food.foodrunnerapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.food.foodrunnerapplication.R
import com.food.foodrunnerapplication.UpdatePassword
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class resetPasswordActivity : AppCompatActivity() {


    lateinit var passwordView : EditText
    lateinit var confirmPasswordView : EditText
    lateinit var btn : Button
    lateinit var database :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val mobile = intent.getStringExtra("mobile")!!
        passwordView = findViewById(R.id.etPasswordResetPage)
        confirmPasswordView = findViewById(R.id.etConfirmPasswordReset)
        btn = findViewById(R.id.btnNextReset)


        btn.setOnClickListener {

            val dbPassword = passwordView.text.toString()
            val dbConfirmPassword = confirmPasswordView.text.toString()

            if(dbPassword.isNotEmpty() && dbConfirmPassword.isNotEmpty()){

                if(dbPassword == dbConfirmPassword){
                    database = FirebaseDatabase.getInstance().getReference("user")

                    database.child(mobile).get().addOnSuccessListener {
                        val dbName = it.child("name").value as String
                        val dbEmail = it.child("email").value as String
                        val dbMobile = it.child("mobileNumber").value as String
                        val dbAddress = it.child("address").value as String
                        var userDetails = mapOf<String,String>(
                            "name" to dbName,
                            "address" to dbAddress,
                            "email" to dbEmail,
                            "mobileNumber" to dbMobile,
                            "password" to dbPassword
                        )

                        database.child(mobile).updateChildren(userDetails).addOnSuccessListener {
                            Toast.makeText(this,"updated successfully",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@resetPasswordActivity,LoginActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener{
                            Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show()
                        }

                    }.addOnFailureListener {
                        Toast.makeText(this,"Failed to fetch the value",Toast.LENGTH_SHORT).show()
                    }


                }else{
                    Toast.makeText(this,"Password mismatch",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fill all the fields first",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onPause(){
        super.onPause()
        finish()
    }
}