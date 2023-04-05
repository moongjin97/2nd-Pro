package com.insta

import java.time.LocalDateTime

data class JoinSaveDto (

    var userNo:String?,
    var userId:String?,
    var userPw:String?,
    var userNm:String?,
    var userPhone:String?,
    var userEmail:String?,
    var userJoinDt:LocalDateTime?,
    var userDelDt:LocalDateTime?,
    var userSt:String?

    )
