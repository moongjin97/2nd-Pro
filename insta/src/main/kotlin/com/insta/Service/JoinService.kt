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
        var findUser = userRepository.findByUserEmail(findInfoCheck)
        var userId = findUser.userId
        var userEmail = findUser.userEmail

        try{

            val htmlContent = """
            <div class="wrap" style="margin: 100px auto; width: 450px;display: flex; flex-direction: column;">
            <div>
            <img src="http://drive.google.com/uc?export=view&id=1oArh9BFHw1K2Gc5dsYzs_ZQMt6JAuQeE"
            alt="인스타그램이미지"
            id="insta"
            style="width: 200px; height: 50px;"
            />
            </div>
            <p id="greet" style="font-size: 20px;"><span>${userId}</span>님 안녕하세요.</p>
            <p id="content" style="font-size: 20px;">
            Instagram 로그인과 관련하여 불편을 끼쳐 드려서 죄송합니다. 비밀번호를
            잊으셨나요? 회원님이 로그인한것이 맞다면 지금바로 비밀번호를 재설정 할
            수 있습니다.
            </p>
            <a href="http://localhost:8080/newPw/${userEmail}" style="text-decoration: none; color:white;">
            <button id="button"
            style="width: 100%; height: 50px;
            background-color: #47a2ea;
            border: solid 1px #009fdf;
            border-radius: 3px;
            font-size: 20px;
            color: white;
            font-weight: bold;
            padding-top: 30px;
            padding-bottom: 30px;
            display: flex;
            justify-content: center;
            align-items: center;"
            >
            비밀번호 재설정
            </button>
            </a>
            <div id="metaDiv" style="margin-top: 50px; text-align: center; padding-bottom: 50px;">
            <img src="http://drive.google.com/uc?export=view&id=1Max2S-mP-GpssexCdfo2obS4OYpcSqK-"
            alt="메타이미지"
            id="meta"
            style="width: 60px; height: 30px;"
            />
            </div>
            <div id="mataMal"
            style="font-size: 12px; text-align: center; padding-bottom: 10px; color: #929294;">
            © Instagram. Meta Platforms, Inc., 1601 Willow Road, Menlo Park, CA 94025
            </div>
            <div id="msg" style="font-size: 12px; text-align: center; padding: auto; color: #929294;">
            이 메시지는 <span>${userEmail}</span> 주소로
            <span>${userId}</span>님에게 전송된 메시지입니다. 회원님의 계정이 아닌가요?
            이 계정에서 회원님의 이메일을 삭제하세요.
            </div>
            </div>
            """


            val mimeMessage = javaMailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")

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

    fun changePassword(userEmail: String, newPassword: String): Boolean {
        var UserInfo = userRepository.findByUserEmail(userEmail)
        if(UserInfo == null){
            return false
        }else{
            try{
                var newPw = crypto(newPassword)
                UserInfo.userPw = newPw
                userRepository.save(UserInfo)
            }catch (e:Exception){
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    fun findname(userId: String): String {
        var username = userRepository.findByUserId(userId)
        return username.userNm
    }

    fun findUserNumber(userId: String):Users {
        return userRepository.findByUserId(userId)
    }

    fun userInfoDto(userInfo: Users): JoinSaveDto {
        var joinSaveDto = JoinSaveDto(null,null,null,null,null,null,null,null,null)
        joinSaveDto.userNo = userInfo.userNo
        joinSaveDto.userId = userInfo.userId
        joinSaveDto.userPw = userInfo.userPw
        joinSaveDto.userNm = userInfo.userNm
        joinSaveDto.userPhone = userInfo.userPhone
        joinSaveDto.userEmail = userInfo.userEmail
        joinSaveDto.userJoinDt = userInfo.userJoinDt
        joinSaveDto.userDelDt = userInfo.userDelDt
        joinSaveDto.userSt = userInfo.userSt
        return joinSaveDto
    }
}

