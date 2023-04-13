package com.insta.Dto

import com.insta.Entity.Users
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Column

data class JoinSaveDto (

    var userNo:Int?,
    var userId:String?,
    var userPw:String?,
    var userNm:String?,
    var userIntro:String?,
    var userPhone:String?,
    var userEmail:String?,

    var userJoinDt:LocalDateTime?,
    var userDelDt:LocalDateTime?,
    var userSt:String?

    ) {
    fun toEntity(): Users {
        return Users(
//            userNo = userNo?:0,
            userId = userId ?: "",
            userPw = userPw ?: "",
            userNm = userNm ?: "",
            userIntro = userIntro ?: "",
            userPhone = userPhone ?: null,
            userEmail = userEmail ?: null,
            userJoinDt = userJoinDt ?: LocalDateTime.now(),
            userDelDt = userDelDt ?: null,
            userSt = userSt ?: "회원"
        )
    }

    fun toUpdateEntity(): Users {
        return Users(
            userNo = userNo?:0,
            userId = userId ?: "",
            userPw = userPw ?: "",
            userNm = userNm ?: "",
            userIntro = userIntro ?: "",
            userPhone = userPhone ?: null,
            userEmail = userEmail ?: null,
            userJoinDt = userJoinDt ?: LocalDateTime.now(),
            userDelDt = userDelDt ?: null,
            userSt = userSt ?: "회원"
        )
    }


}
