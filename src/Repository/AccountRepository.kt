package com.example.Repository

import com.example.database.AppDatabase
import com.example.database.table.accounts
import com.example.dto.Account
import io.ktor.request.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AccountRepository {
    init {
        transaction {
            AppDatabase.getInstance().createAccountSchema()
        }
    }

    fun getAccountsList() = transaction {
        var listAccounts: MutableList<Account> = ArrayList()
        val query = accounts.selectAll()
        query.forEach {
            if (rowToAccount(it) != null)
                listAccounts.add(rowToAccount(it)!!)
        }
        listAccounts
    }

    fun getAccountByUsername(username: String) =
        transaction {
            accounts.select { accounts.username eq username }
                .map { rowToAccount(it) }
                .firstOrNull()
        }

    private fun rowToAccount(row: ResultRow?): Account?{
        return if (row == null)
            null
        else
            Account(
                id = row[accounts.id],
                username = row[accounts.username],
                password = row[accounts.password],
                email = row[accounts.email]
            )
    }

    private fun removeAccountById(id: Int) = transaction {
        accounts.deleteWhere {
            accounts.id eq id
        }
    }
}