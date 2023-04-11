package com.insta.Controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

@Controller
class MainController {
    @GetMapping("/")
    fun index():String{
        return "index"
    }

    @GetMapping("/main")
    fun mainForm():String{
        return "mainForm"
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
    @GetMapping("/newPw/{userEmail}")
    fun newPw(@PathVariable ("userEmail") userEmail:String, model: Model):String{
        model.addAttribute("userEmail", userEmail)
        return "newPwSetting"
    }
}