package com.example.config

import com.example.Repository.AccountRepository
import com.example.controller.AccountController
import com.example.database.AppDatabase
import com.example.service.AccountService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ModuleConfig{
    private val accountModule = Kodein.Module("ACCOUNT"){
        bind() from singleton { AccountRepository() }
        bind() from singleton { AccountService(instance()) }
        bind() from singleton { AccountController(instance()) }
    }

    internal fun kodein() = Kodein{
        import(accountModule)
    }
}