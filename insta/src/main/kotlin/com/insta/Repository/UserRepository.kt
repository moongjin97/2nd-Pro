package com.insta.Repository

import com.insta.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository :JpaRepository<Users,Int> {
  fun findByUserId(userId: String): Users
}