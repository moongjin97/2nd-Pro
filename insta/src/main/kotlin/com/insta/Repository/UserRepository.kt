package com.insta.Repository

import com.insta.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository :JpaRepository<Users,Int> {
  //아이디로찾기
  fun findByUserId(userId: String): Users

    @Query(value = "select * from USERS where USER_ID = :user_ID_param", nativeQuery = true)
    fun findByUserRole(@Param("USER_ID_param") userId: String): Optional<Users>

  // 폰넘버로찾기
     fun findByUserPhone(findInfo: String): Users
     //이메일로 찾기
     fun findByUserEmail(findInfo: String): Users

}