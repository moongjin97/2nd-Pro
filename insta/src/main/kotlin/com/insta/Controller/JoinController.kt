package com.insta.Controller


import com.insta.Dto.JoinFirstDto
import com.insta.Dto.JoinSaveDto
import com.insta.Repository.UserRepository
import com.insta.Service.JoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class JoinController {
    @Autowired
    lateinit var joinService: JoinService

    @PostMapping("/join")
    @ResponseBody
    fun join(@ModelAttribute joinFirstDto: JoinFirstDto): String {

        var saveResult = joinService.saveJoin(joinFirstDto)
        if (!saveResult) {
            return "<script>alert('이미 사용중인 사용자 이름입니다.');history.back();</script>"
        }
    return "<script>alert('회원가입 되셨습니다.'); location.href='/'</script>"
    }
}