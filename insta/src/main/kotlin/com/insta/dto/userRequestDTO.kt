package com.insta.dto

import com.insta.entity.User
import java.time.LocalDateTime


data class userRequestDTO(

    private val userNo: Int,
    private val userId: String,
    private val userPw: String,
    private val userPhone: String,
    private val userEmail: String,
    private val joinDt: LocalDateTime,
    private val userSt: String) {


    fun toSaveEntity(): User {
        return User(
            userNo = this.userNo,
            userId = this.userId,
            userPw = this.userPw,
            userPhone = this.userPhone,
            userEmail = this.userEmail,
            joinDt = this.joinDt,
            userSt = this.userSt
        )
    }


}