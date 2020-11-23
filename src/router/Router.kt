package com.example.router

import com.example.controller.AccountController
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.Account(accountController: AccountController){
    route("accounts"){
        get {
            accountController.getAllAccounts(this.context)
        }

        get("/{username}"){
            val username = this.context.parameters["username"]
            accountController.getAccountsByUsername(this.context, username = username)
        }

        post("/info"){
            val token = this.context.receiveParameters()["x-access-token"] as String
            println("token received -> $token")
            accountController.getAccountByToken(this.context, token)
        }

        post("/login"){
            val username = this.context.receiveParameters()["username"] as String
            accountController.getTokenByUsername(this.context, username)
        }
    }
}