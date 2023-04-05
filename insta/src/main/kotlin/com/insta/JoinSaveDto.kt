package com.insta

import java.time.LocalDateTime

data class JoinSaveDto (

    private var userNo:String?,
    private var userId:String?,
    private var userPw:String?,
    private var userNm:String?,
    var userPhone:String?,
    var userEmail:String?,
    private var userJoinDt:LocalDateTime?,
    private var userDelDt:LocalDateTime?,
    private var userSt:String?

    )
