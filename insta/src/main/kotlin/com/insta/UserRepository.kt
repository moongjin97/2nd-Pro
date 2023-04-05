package com.insta

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository :JpaRepository<Users,Int> {
}