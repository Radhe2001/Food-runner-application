package com.food.foodrunnerapplication.fragment

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.food.foodrunnerapplication.R
import com.food.foodrunnerapplication.User
import com.food.foodrunnerapplication.adapter.DashboardRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Objects



class DashboardFragment : Fragment() {


    //declaring variable for recycle view
    lateinit var recyclerView : RecyclerView
    //declaring variable for fragment manager : because it is present inside the recycler view class hence we use this class to declare it
    lateinit var layoutManager : RecyclerView.LayoutManager
    //declaring a variable for dashboard adapter
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    //making an array which will contain the data to be stored
    lateinit var database : DatabaseReference
    lateinit var restaurantArrayList:ArrayList<User>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //initializing the recyclerview by its id but since it is a activity is not there we use the inflater to initialize it
        recyclerView = view.findViewById(R.id.recyclerViewDashboard)
        //since it is a fragment but the fragment layout takes the activity as parameter so we pass the main page activity into it
        // main page activity is denoted by activity
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        restaurantArrayList = arrayListOf()



        database = FirebaseDatabase.getInstance().getReference("restaurants")

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(reference in snapshot.children){
                        val name = reference.child("name").value as String
                        val rating = reference.child("rating").value as String
                        val address = reference.child("address").value as String
                        val minPrice = reference.child("minPrice").value as String
                        val user = User(name,rating,address,minPrice,false)
                        restaurantArrayList.add(user)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Error occured",Toast.LENGTH_SHORT).show()
            }

        })
        recyclerView.adapter = DashboardRecyclerAdapter(activity as Context,restaurantArrayList)

        return view
    }


}