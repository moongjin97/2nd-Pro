package com.insta.Service

import com.insta.Dto.JoinFirstDto
import com.insta.Dto.JoinSaveDto
import com.insta.Dto.LoginDto
import com.insta.Entity.Users
import com.insta.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.mail.internet.MimeMessage

@Service
class JoinService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var javaMailSender: JavaMailSender

    //비밀번호 암호화 펑션 SHA-256 버전
    fun crypto(ss:String):String{
        val sha = MessageDigest.getInstance("SHA-256")
        val hexa = sha.digest(ss.toByteArray())
        val crypto_str = hexa.fold("",{str, it -> str + "%02x".format(it)})
        return crypto_str
    }


    fun saveJoin (joinFirstDto: JoinFirstDto):Boolean{
        println(joinFirstDto.toString())
        var joinSaveDto = JoinSaveDto(null,null,null,null,null,null,null,null,null)
        val PaE = joinFirstDto.userPaE
        println(" 나온값 ${PaE}")

        val regex = Regex("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$")

        val regex2 = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

        joinSaveDto.userId = joinFirstDto.userId

        var pw = crypto(joinFirstDto.userPw.toString())
        joinSaveDto.userPw = pw
        joinSaveDto.userNm = joinFirstDto.userNm

        if(regex.matches(PaE!!)){
            joinSaveDto.userPhone = PaE
        }else if(regex2.matches(PaE.toString())){
            joinSaveDto.userEmail = PaE
        }
        println("투스트링없는것 이메일:"+joinSaveDto.userEmail)
        println("투스트링없는것 핸드폰:"+joinSaveDto.userPhone)

        try{
            userRepository.save(joinSaveDto.toEntity())
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
        return true
    }



    fun login(loginDto: LoginDto): Int {

        var loginCheck = userRepository.findByUserId(loginDto.userId.toString())
        var loginDtoUserPw = crypto(loginDto.userPw.toString())
        if(loginCheck != null){
          if(loginCheck.userPw.equals(loginDtoUserPw)){
              return 0 // 비밀번호 일치시
          }else{
              return 1 // 불일치시
          }
      }else{
        return 2 // 아이디가 없을시
        }
}

    fun findInfoCheck(findInfo: String): String? {

        //핸드폰 정규식
        val regex = Regex("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$")
        //이메일 정규식
        val regex2 = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        var userEmail: String? = null
        if(regex.matches(findInfo)){//핸드폰 번호라면
            var user = userRepository.findByUserPhone(findInfo)
            userEmail = user.userEmail
            if(user.userEmail == null){// 핸드폰번호로 조회했을 때 유저의 email 이 없으면
                return "0"
            }
        }else if(regex2.matches((findInfo))){// 이메일이라면
            var user = userRepository.findByUserEmail(findInfo)
            userEmail = user.userEmail
            if(user == null){// email 로 조회했을때 유저 정보가 없으면
                return "0"
            }
        }else{//그외의경우라면
            var user = userRepository.findByUserId(findInfo)
            userEmail = user.userEmail
            if(user.userEmail == null){// 그외( 유저 아이디로 조회했을때 ) 유저의 email 이 없으면
                return "0"
            }
        }
            return userEmail // 유저정보가 있고 email 정보가 있을 때
    }


    fun sendEmail(findInfoCheck: String): Boolean {

        try{
            val mimeMessage = javaMailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
            //html 파일 읽어 오기
            val htmlFile = ClassPathResource("templates/pwSetting.html").file
            val htmlContent = String(htmlFile.readBytes(), StandardCharsets.UTF_8)

            mimeMessageHelper.setTo(findInfoCheck)
            mimeMessageHelper.setFrom("mysoho2023@naver.com")
            mimeMessageHelper.setSubject("인스타그램 비밀번호계정 관련 이메일입니다.")
            mimeMessageHelper.setText(htmlContent, true)
            javaMailSender.send(mimeMessage)
            return true

        }catch (e:Exception){
            e.printStackTrace()
            return false
        }
    }
}

