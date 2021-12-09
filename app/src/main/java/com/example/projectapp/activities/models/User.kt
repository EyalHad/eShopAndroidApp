package com.example.projectapp.activities.models

class User(private val id :String ="", val firstName:String = "", val lastName:String = "",
           val email:String = "", val image:String = "", val gender:String = "", val profileCompleted:Int = 0){
    fun getId():String{return id}
}
