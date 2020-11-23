package com.example.database

import com.example.database.table.accounts
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

class AppDatabase {
    companion object{
        private var appDb: AppDatabase? = null

        fun getInstance(): AppDatabase {

            return appDb?:AppDatabase()
        }
    }

    private lateinit var database:Database

    private val host = "localhost:5432"
    private val dbName = "testktor"
    private val dbDriver = "org.postgresql.Driver"
    private val dbUsername = "thanhnh"
    private val dbPassword = "thanhnh98"
    private val connectionUrl = "jdbc:postgresql://$host/$dbName"

    fun connect(){
        database = Database.connect(url = connectionUrl, driver = dbDriver, user = dbUsername, password = dbPassword)
        println("connected db: $database")
    }

    fun createAccountSchema(){
        SchemaUtils.create(accounts)
    }
}