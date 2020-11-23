package com.example.service

import com.example.Repository.AccountRepository

class AccountService(private val accountRepository: AccountRepository){
    fun getAllAccounts() = accountRepository.getAccountsList()
    fun getAccount(username: String) = accountRepository.getAccountByUsername(username)
}