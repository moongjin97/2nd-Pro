package com.insta.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userNo: Int,

    @Column(name = "user_ID")
    var userId: String,

    @Column(name = "user_PW")
    var userPw: String,

    @Column(name = "USER_NM")
    var userNm: String,

    @Column(name = "USER_PHONE")
    var userPhone: String?,

    @Column(name = "USER_EMAIL")
    var userEmail: String?,

    @Column(name = "JOIN_DT")
    var joinDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "DEL_DT")
    var delDt: LocalDateTime? = null,

    @Column(name = "USER_ST")
    var userSt: String? = null
)