package com.insta.Entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "BOARD")
class Board (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_NO")
    val boardNo:Int? =null,
    @Column(name="USER_ID")
    val userId: String?,
    @Column(name = "BOARD_CONTENT")
    val boardContent : String?,
    @Column(name = "BOARD_PICTURE")
    val boardPicture : String?,
    @CreationTimestamp
    @Column(updatable = false, name = "WRITE_TIME")
    val writeTime :LocalDateTime?,
    @UpdateTimestamp
    @Column(insertable = false, name = "UPDATE_TIME")
    val updateTime :LocalDateTime?,
    @Column(name = "Board_LIKES")
    val boardLikes : Int?
)