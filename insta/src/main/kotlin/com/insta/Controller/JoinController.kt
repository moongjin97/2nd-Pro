package com.insta.Controller


import com.insta.Dto.JoinFirstDto
import com.insta.Dto.JoinSaveDto
import com.insta.Dto.LoginDto
import com.insta.Repository.UserRepository
import com.insta.Service.JoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession


@Controller
class JoinController {
    @Autowired
    lateinit var joinService: JoinService


    //회원가입 액션받기
    @PostMapping("/join")
    @ResponseBody
    fun join(@ModelAttribute joinFirstDto: JoinFirstDto): String {

        var saveResult = joinService.saveJoin(joinFirstDto)
        if (!saveResult) {
            return "<script>alert('이미 사용중인 사용자 이름입니다.');history.back();</script>"
        }
    return "<script>alert('회원가입 되셨습니다.'); location.href='/'</script>"
    }

    @PostMapping("/login")
    @ResponseBody
    fun login(@ModelAttribute loginDto: LoginDto,
               sesstion: HttpSession):String {
        var loginResult = joinService.login(loginDto)

        if(!loginResult){
            return "<script>alert('아이디와 비밀번호를 확인해주세요.');history.back();</script>"
        }
        sesstion.setAttribute("userId",loginDto.userId)
        return "<script>location.href='/main'</script>"
    }

}