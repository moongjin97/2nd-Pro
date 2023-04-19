package com.insta.Security

import com.insta.Entity.Users
import com.insta.Repository.UserRepository
import com.insta.enumeration.UserRole
import lombok.RequiredArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@RequiredArgsConstructor
@Service
class SecurityService (private val userRepository: UserRepository) : UserDetailsService {

    //사용자 아이디를 통해, 사용자 정보와 권한을 스프링시큐리티에 전달한다.
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        // 실DB를 통해서 사용자 정보를 시큐리티에 전달한다.
        val _optMember: Optional<Users> = this.userRepository.findByUserRole(username)
        if (_optMember.isPresent) {
            throw UsernameNotFoundException("사용자를 찾을수 없습니다.")
        }
        val user: Users = _optMember.get()
        val authorities: MutableList<GrantedAuthority> = ArrayList<GrantedAuthority>()
        if (user.userRole == "ROLE_ADMIN") {
            authorities.add(SimpleGrantedAuthority(UserRole.ADMIN.value))
        } else {
            authorities.add(SimpleGrantedAuthority(UserRole.USER.value))
        }
        return User(user.userId, user.userPw, authorities)
    }
}