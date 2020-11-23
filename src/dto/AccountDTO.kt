package com.example.dto

import com.example.jwt.JwtProvider
import com.google.gson.Gson

class AccountDTO (private val account: Account) {
    //helper func
}

data class Account(val id: Int?, val username: String?, val password: String?, val email: String?)