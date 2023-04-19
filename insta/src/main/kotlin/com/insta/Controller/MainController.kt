package com.insta.Controller

import com.insta.Dto.JoinSaveDto
import com.insta.Entity.Users
import com.insta.Service.JoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession
import kotlin.reflect.jvm.internal.impl.load.kotlin.PackagePartProvider.Empty

@Controller
class MainController (private val joinService: JoinService){


    @GetMapping("/")
    fun index(session: HttpSession):String{
        var userId = session.getAttribute("userId") as String?
        println("유저 아이디 ${userId}")
        if(userId == null){
            return "index"
        }else{
            return "mainForm"
        }
    }

    @GetMapping("/main")
    fun mainForm():String{
        return "mainForm"
    }
    //검색 액션받기(CSS 깨짐)
    @GetMapping("/main/search")
    fun search(model: Model,@RequestParam(value = "keyword")keyword:String):String{

        var searchResult = joinService.search(keyword)
        println("결과값 : ${searchResult}")
        model.addAttribute("search", searchResult)
        return "searchResult"
    }

    @GetMapping("/join")
    fun joinForm():String{
        return "join"
    }

    @GetMapping("/findPw")
    fun findPw():String{
        return "resetPw"
    }

    @GetMapping("/pwSetting")
    fun pwSetting():String{
        return "pwSetting"
    }

    @GetMapping("/test")
    fun test():String{
        return "/fragment/header"
    }

    @GetMapping("/profile")
    fun profile():String{
        return "profile"
    }
    @GetMapping("/newPw/{userEmail}")
    fun newPw(@PathVariable ("userEmail") userEmail:String, model: Model):String{
        model.addAttribute("userEmail", userEmail)
        return "newPwSetting"
    }

    @GetMapping("/settingForm/{id}")
    fun settingForm(@PathVariable(value = "id") userId:String, model: Model):String{
        var userInfo = joinService.findUserNumber(userId)
        var userInfoDto = joinService.userInfoDto(userInfo)
        model.addAttribute("userInfo", userInfoDto)
        return "settingForm"
    }
}