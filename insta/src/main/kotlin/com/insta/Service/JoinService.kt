package com.insta.Service

import com.insta.Dto.JoinFirstDto
import com.insta.Dto.JoinSaveDto
import com.insta.Dto.LoginDto
import com.insta.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class JoinService {

    @Autowired
    lateinit var userRepository: UserRepository

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



    fun login(loginDto: LoginDto): Boolean {

        var loginCheck = userRepository.findByUserId(loginDto.userId.toString())
        var loginDtoUserPw = crypto(loginDto.userPw.toString())
        if(loginCheck != null){
          if(loginCheck.userPw.equals(loginDtoUserPw)){
              return true
          }else{
              return false
          }
      }else{
        return false
        }
}
}

