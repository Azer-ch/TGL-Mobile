package com.example.tunisangoldenleague.model

import java.io.Serializable

data class Team (var id : String, var name:String, var image:String, var league : String, var points : Int,var victoires:Int ,var nulles:Int,var pertes:Int,var buts_marque:Int ,var buts_encaisse : Int) :
    Serializable {
}