package com.insta.Dto

import com.insta.Entity.Users
import java.time.LocalDateTime

data class JoinSaveDto (

    var userNo:Int?,
    var userId:String?,
    var userPw:String?,
    var userNm:String?,
    var userPhone:String?,
    var userEmail:String?,
    var userJoinDt:LocalDateTime?,
    var userDelDt:LocalDateTime?,
    var userRole:String?,
    var userSt:String?

    ) {
    fun toEntity(): Users {
        return Users(
//            userNo = userNo?:0,
            userId = userId ?: "",
            userPw = userPw ?: "",
            userNm = userNm ?: "",
            userPhone = userPhone ?: null,
            userEmail = userEmail ?: null,
            userJoinDt = userJoinDt ?: LocalDateTime.now(),
            userDelDt = userDelDt ?: null,
            userRole = userRole ?: "ROLE_USER",
            userSt = userSt ?: "회원"
        )
    }

}
