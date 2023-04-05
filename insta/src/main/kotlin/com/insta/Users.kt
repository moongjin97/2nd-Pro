package com.insta

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val userNo: Int? = null,
    @Column(name = "USER_ID")
    private val userId: String,
    @Column(name = "USER_PW")
    private val userPw: String,
    @Column(name = "USER_NM")
    private val userNm: String,
    @Column(name = "USER_PHONE")
    private val userPhone: String?,
    @Column(name = "USER_EMAIL")
    private val userEmail: String?,
    @Column(name = "JOIN_DT")
    private val userJoinDt: LocalDateTime,
    @Column(name = "DEL_DT")
    private val userDelDt: LocalDateTime?,
    @Column(name = "USER_ST")
    private val userSt: String
)