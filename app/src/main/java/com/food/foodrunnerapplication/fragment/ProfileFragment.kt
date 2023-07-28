package com.food.foodrunnerapplication.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.food.foodrunnerapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    lateinit var nameView : TextView
    lateinit var addressView : TextView
    lateinit var contactView : TextView
    lateinit var emailView : TextView
    lateinit var database:DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameView = view.findViewById(R.id.txtEnterName)
        emailView = view.findViewById(R.id.txtEnterEmail)
        contactView = view.findViewById(R.id.txtEnterContact)
        addressView = view.findViewById(R.id.txtEnterAddress)
        sharedPreferences = requireContext().getSharedPreferences("Login_Details",Context.MODE_PRIVATE)
        val number = sharedPreferences.getString("mobile","")!!

        database = FirebaseDatabase.getInstance().getReference("user")
        database.child(number).get().addOnSuccessListener {
            nameView.text = it.child("name").value as String
            addressView.text = it.child("address").value as String
            contactView.text = it.child("mobileNumber").value as String
            emailView.text = it.child("email").value as String

        }.addOnFailureListener{
            Toast.makeText(context,"Failed to get the details",Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(context,number,Toast.LENGTH_SHORT).show()



        return view
    }
}