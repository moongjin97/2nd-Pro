package com.insta.Dto

import com.insta.Entity.Board
import com.insta.Entity.Users
import java.time.LocalDateTime

class BoardDto (

    var boardNo :Int?,
    var userId : String?,
    var boardContent: String?,
    var boardPicture: String?,
    var writeTime : LocalDateTime?,
    var updateTime : LocalDateTime?,
    var boardLike : Int?,

) {

    fun toEntity(): Board {
        return Board(
            userId = userId ?: "",
            boardContent = boardContent ?: "",
            boardPicture = boardPicture ?: "",
            writeTime = writeTime ?: LocalDateTime.now(),
            updateTime = updateTime ?: null,
            boardLikes = boardLike ?: 0,
        )

    }
}