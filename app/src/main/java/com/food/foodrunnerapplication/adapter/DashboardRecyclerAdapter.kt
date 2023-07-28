package com.food.foodrunnerapplication.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.food.foodrunnerapplication.FavouriteDetails
import com.food.foodrunnerapplication.R
import com.food.foodrunnerapplication.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DashboardRecyclerAdapter(val context:Context,var restaurantList:ArrayList<User>/*,var addressArray:ArrayList<String>,var ratingArray:ArrayList<String>,var minPriceArray:ArrayList<String>*/) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    lateinit var database:DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    //this method is used to create the views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_item_view,parent,false)
        return DashboardViewHolder(view)
    }

    //this method is used to return the total size of the arrayList
    override fun getItemCount(): Int {
        return restaurantList.size
    }

    //this method is used to set the data to the view
    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.name.text = restaurantList[position].name
        holder.rating.text = restaurantList[position].rating
        holder.address.text = restaurantList[position].address
        holder.minPrice.text = restaurantList[position].minPrice

        holder.favourite.setOnClickListener {

            val mobile = sharedPreferences.getString("mobile","")!!
            database = FirebaseDatabase.getInstance().getReference("favourite/${mobile}/${restaurantList[position].name}")
            val mobileNode :DatabaseReference = database.child(mobile)
            val fav = (!restaurantList[position].fav).toString()
            val user = FavouriteDetails(restaurantList[position].name,restaurantList[position].rating,restaurantList[position].address,
                restaurantList[position].minPrice,fav)

            val restaurantNode:DatabaseReference = mobileNode.child(restaurantList[position].name)

            /*val node =  database.child(mobile).child(restaurantList[position].name)*/
        /*restaurantNode*/database.setValue(user).addOnSuccessListener {
            Toast.makeText(context,"saved to favourite",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context,"failed to save to favourite",Toast.LENGTH_SHORT).show()
        }

//            val fav = (!restaurantList[position].fav).toString()
//            val user = FavouriteDetails(restaurantList[position].name,restaurantList[position].rating,restaurantList[position].address,
//                restaurantList[position].minPrice,fav)
//            node.child(restaurantList[position].name).setValue(user).addOnSuccessListener {
//                Toast.makeText(context,"saved to favourite",Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(context,"failed to save to favourite",Toast.LENGTH_SHORT).show()
//            }
//            restaurantList[position].fav = true

//            if(!restaurantList[position].fav){

//            }else{
//                node.child(restaurantList[position].name).get().addOnSuccessListener {
//                    val name = it.child("name").value as String
//                    val rating = it.child("rating").value as String
//                    val address = it.child("address").value as String
//                    val minPrice = it.child("minPrice").value as String
//                    val fav = it.child("fav").value as String
//                    if(fav == "true"){
//                        val newUser = mapOf<String,String>(
//                            "name" to name,
//                            "rating" to rating,
//                            "address" to address,
//                            "minPrice" to minPrice,
//                            "fav" to "false"
//                        )
//
//                        node.child(name).updateChildren(newUser).addOnSuccessListener {
//                            Toast.makeText(context,"added to favourite",Toast.LENGTH_SHORT).show()
//                        }.addOnFailureListener {
//                            Toast.makeText(context,"failed to save to favourite",Toast.LENGTH_SHORT).show()
//                        }
//                    }else{
//                        val newUser = mapOf<String,String>(
//                            "name" to name,
//                            "rating" to rating,
//                            "address" to address,
//                            "minPrice" to minPrice,
//                            "fav" to "true"
//                        )
//                        node.child(name).updateChildren(newUser).addOnSuccessListener {
//                            Toast.makeText(context,"removed from favourite",Toast.LENGTH_SHORT).show()
//                        }.addOnFailureListener {
//                            Toast.makeText(context,"failed to save to favourite",Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
        }
    }


    class DashboardViewHolder(view:View): RecyclerView.ViewHolder(view){
        var fav:Boolean = false
        val name : TextView = view.findViewById(R.id.txtRestaurantName)
        val rating : TextView = view.findViewById(R.id.txtRating)
        val address : TextView = view.findViewById(R.id.txtRestaurantAddress)
        val minPrice : TextView = view.findViewById(R.id.txtMinPerHeadCost)
        val favourite : ImageButton = view.findViewById(R.id.imgHeart)
    }
}