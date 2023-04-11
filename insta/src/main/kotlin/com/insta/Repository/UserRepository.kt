package com.insta.Repository

import com.insta.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository :JpaRepository<Users,Int> {
  //아이디로찾기
  fun findByUserId(userId: String): Users
  // 폰넘버로찾기
     fun findByUserPhone(findInfo: String): Users
     //이메일로 찾기
     fun findByUserEmail(findInfo: String): Users

}