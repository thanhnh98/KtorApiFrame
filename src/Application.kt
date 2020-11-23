package com.example

import com.example.config.ModuleConfig
import com.example.controller.AccountController
import com.example.database.AppDatabase
import com.example.router.Account
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.kodein.di.generic.instance

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {

    AppDatabase.getInstance().connect()
    val accountController by ModuleConfig.kodein().instance<AccountController>()

    //install
    install(Locations) {}

    install(Routing){
        Account(accountController)
    }

    install(Authentication) {}

    install(ContentNegotiation) {
        gson {
        }
    }
}

