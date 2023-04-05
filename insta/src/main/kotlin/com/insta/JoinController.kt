package com.insta


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class JoinController {

    @PostMapping("/join")
    @ResponseBody
    fun join(@ModelAttribute joinFirstDto: JoinFirstDto):String{
        println(joinFirstDto.toString())
        var joinSaveDto = JoinSaveDto(null,null,null,null,null,null,null,null,null)
        val PaE = joinFirstDto.userPaE
        println(" 나온값 ${PaE}")
        val regex = Regex("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$")
        val regex2 = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

        joinSaveDto.userId = joinFirstDto.userId
        joinSaveDto.userPw = joinFirstDto.userPw
        joinSaveDto.userNm = joinFirstDto.userNm

        if(regex.matches(PaE!!)){
            joinSaveDto.userPhone = PaE

        } else if(regex2.matches(PaE.toString())){
            joinSaveDto.userEmail = PaE

        }

        return "일단은"
    }


}