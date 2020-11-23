package com.example.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table

object accounts: Table() {
    val id: Column<Int> = integer("user_id").autoIncrement()
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
    val username: Column<String> = varchar("username", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 50)
    val email: Column<String> = varchar("email", 50)
}