package com.example.tunisangoldenleague.model

import java.io.Serializable

data class Team (var name:String, var image:String, var league : String, var points : Int) :
    Serializable {
}