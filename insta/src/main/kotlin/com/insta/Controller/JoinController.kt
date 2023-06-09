package com.insta.Controller


import com.insta.Dto.JoinFirstDto
import com.insta.Dto.JoinSaveDto
import com.insta.Dto.LoginDto
import com.insta.Repository.UserRepository
import com.insta.Service.JoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
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

    //로그인 액션받기
    @PostMapping("/login")
    @ResponseBody
    fun login(@ModelAttribute loginDto: LoginDto,
               session: HttpSession):String {

        var loginResult = joinService.login(loginDto)
        if(loginResult == 1){
            return "<script>alert('비밀번호가 일치하지 않습니다.');history.back();</script>"
        }else if(loginResult == 2){
            return "<script>alert('등록되지않은 사용자 이름입니다. 회원가입을해주세요.');history.back();</script>"
        }else{
            session.setAttribute("userId",loginDto.userId)
            var userName = joinService.findname(loginDto.userId!!)
            session.setAttribute("userName",userName)
            return "<script>location.href='/main'</script>"
        }

    }

    @PostMapping("/findAction")
    @ResponseBody
    fun findAction (@RequestParam(value ="findInfo") findInfo:String):String{

        var findInfoCheck = joinService.findInfoCheck(findInfo)
        if(findInfoCheck.equals("0")){
            return "<script>alert('등록된 이메일이 없습니다. 다른정보를 입력해주세요.')</script>"
        }else{
            var sendEmail = joinService.sendEmail(findInfoCheck!!)
            println("결과값 ${sendEmail}")
            if(!sendEmail){
                return "<script>alert('등록된 이메일로 링크 발송을 실패했습니다.'); history.back();</script>"
            }
            return "<script> alert('계정에 다시 로그인 할 수 있는 링크가 포함된\\n" +
                    " 이메일을 ${findInfoCheck} 주소로 보내드\\n" +
                    " 렸습니다.'); history.back(); </script>";
        }
    }

    @PostMapping("/newPasswordSetting")
    @ResponseBody
    fun newPasswordSetting(@RequestParam(value = "userEmail") userEmail:String,
                           @RequestParam(value = "newPassword") newPassword:String):String{

        var passwordChange = joinService.changePassword(userEmail,newPassword)
        if(!passwordChange){
            return "<script>alert('비밀번호 변경 실패'); location.href='/'</script>"
        }
        return "<script>alert('비밀번호 변경 성공'); location.href='/main'</script>"
    }
    @PostMapping("/updateUserinfo")
    @ResponseBody
    fun updateUserinfo(@ModelAttribute joinSaveDto: JoinSaveDto):String{

        var updateResult = joinService.update(joinSaveDto)
        if(!updateResult)
            return "<script>alert('정보 변경에 실패했습니다.');history.back();</script>"
        return "<script>alert('정보가 변경되었습니다.'); location.href='/main'</script>"
    }

    @GetMapping("/logout")
    @ResponseBody
    fun logout(session: HttpSession):String{
        session.invalidate()
        return "<script>alert('로그아웃 되었습니다.');location.href='/'</script>"
    }
}