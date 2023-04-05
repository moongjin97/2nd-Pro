package com.insta

import lombok.Getter

@Getter
data class JoinFirstDto(
    private var userId:String?,
    private var userNm:String?,
    private var userPw:String?,
    var userPaE:String?,
)
