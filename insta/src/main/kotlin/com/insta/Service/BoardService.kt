package com.insta.Service

import com.insta.Dto.BoardDto
import com.insta.Repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService (private val boardRepository: BoardRepository){

    fun writeAction(boardDto: BoardDto): Boolean {
        try {
            boardRepository.save(boardDto.toEntity())

        }catch (e:Exception){
        e.printStackTrace()
            return false
        }
        return true
    }
}