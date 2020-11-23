package com.example.controller

import com.auth0.jwt.JWT
import com.example.database.table.accounts
import com.example.dto.Account
import com.example.dto.AccountDTO
import com.example.exception.BaseException
import com.example.jwt.JwtProvider
import com.example.model.BaseResponse
import com.example.service.AccountService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.response.*

class AccountController (private val accountService: AccountService){
    suspend fun getAllAccounts(ctxCall: ApplicationCall) = accountService.getAllAccounts().let {
        ctxCall.respond(it)
    }

    suspend fun getAccountsByUsername(ctxCall: ApplicationCall, username: String?) {
        if (username.isNullOrEmpty())
            return ctxCall.respond(BaseResponse(error = BaseException(message = "username is null or empty")))

        return accountService.getAccount(username).let {
            if (it == null){
                return ctxCall.respond(BaseResponse(error = BaseException(message = "nothing matches")))
            }else
                ctxCall.respond(BaseResponse(data = it))
        }
    }

    suspend fun getAccountByToken(ctxCall: ApplicationCall, token: String){
        try {
            val decode: String = JwtProvider.decodeJWT(token).getClaim("account").asString()
            val account: Account = Gson().fromJson(decode, Account::class.java)
            return accountService.getAccount(account.username!!).let {
                if (it == null){
                    return ctxCall.respond(BaseResponse(error = BaseException(message = "nothing matches")))
                }else
                    ctxCall.respond(BaseResponse(data = it))
            }
        }catch (e: Exception){
            ctxCall.respond(BaseResponse(error = BaseException(message = "invalid token")))
        }
    }

    suspend fun getTokenByUsername(ctxCall: ApplicationCall, username: String?){
        if (username.isNullOrEmpty())
            return ctxCall.respond(BaseResponse(error = BaseException(message = "username is empty")))

        accountService.getAccount(username).let {
            return ctxCall.respond(
                if (it == null)
                    ctxCall.respond(BaseResponse(error = BaseException(message = "nothing matches")))
                else
                    BaseResponse(data = mapOf("x-access-token" to JwtProvider.createJWT(it)))
            )
        }
    }
}