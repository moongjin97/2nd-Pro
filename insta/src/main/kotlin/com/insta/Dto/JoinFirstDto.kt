package com.insta.Dto

import lombok.Getter

@Getter
data class JoinFirstDto(
    var userId:String?,
    var userNm:String?,
    var userPw:String?,
    var userPaE:String?,
)
