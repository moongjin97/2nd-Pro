package com.insta.Controller

import com.insta.Dto.BoardDto
import com.insta.Service.BoardService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class BoardController (private val boardService: BoardService){

    //글 작성
    @PostMapping("/boardWrite")
    @ResponseBody
    fun boardWrite (@ModelAttribute boardDto:BoardDto): String{
        println("dto ${boardDto.boardPicture}")
        val writeActionResult = boardService.writeAction(boardDto)
        if(!writeActionResult)
            return "<script>alert('글 작성에 실패했습니다.'); history.back();</script>"
        return "<script>alert('글이 작성되었습니다.'); location.href='/main';</script>"
    }
}