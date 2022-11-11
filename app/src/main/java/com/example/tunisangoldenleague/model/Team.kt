package com.example.tunisangoldenleague.model

import com.example.tunisangoldenleague.enum.DivisionEnum
import java.io.Serializable

class Team (var name:String, var logo:String, var league : String, var points : Int) :
    Serializable {
}