package com.insta

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {
    @GetMapping("/")
    fun index(model: Model):String{
        return "index"
    }

    @GetMapping("/main")
    fun mainForm(model: Model):String{
        return "mainForm22"
    }
    @GetMapping("/join")
    fun joinForm(model: Model):String{
        return "join"
    }

    @GetMapping("/findPw")
    fun findPw(model: Model):String{
        return "resetPw"
    }
}