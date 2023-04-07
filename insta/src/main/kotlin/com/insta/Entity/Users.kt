package com.insta.Entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNo: Int? = null,
    @Column(name = "USER_ID")
    val userId: String,
    @Column(name = "USER_PW")
    val userPw: String,
    @Column(name = "USER_NM")
    val userNm: String,
    @Column(name = "USER_PHONE")
    val userPhone: String?,
    @Column(name = "USER_EMAIL")
    val userEmail: String?,
    @Column(name = "JOIN_DT")
    val userJoinDt: LocalDateTime,
    @Column(name = "DEL_DT")
    val userDelDt: LocalDateTime?,
    @Column(name = "USER_ST")
    val userSt: String
)