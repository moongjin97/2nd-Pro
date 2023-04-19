package com.insta.Security

import com.insta.Repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RequiredArgsConstructor

@Configuration
@EnableWebSecurity //웹보안 활성화를위한 annotation

class SecurityConfig : WebSecurityConfigurerAdapter() {
//    private val securityService: SecurityService? = null
    private val userRepository: UserRepository? = null
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        //csrf 보안을 비활성화 한다.
        http.csrf().disable();
        //csrf 보안을 쿠키  한다.
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        http
            .authorizeRequests() // 요청에 의한 인증 시작
            //permitAll()는 모든 사용자가 접근할 수 있다는 것을 의미합니다.
            .antMatchers("/css/**").permitAll()
            .antMatchers("/join").permitAll()
            .antMatchers("/").permitAll()
            .and()
            .formLogin() //인증은 formLogin방식으로 하겠다.
            .loginPage("/") //로그인 페이지를 /loginForm URL로 하겠다.
            .loginProcessingUrl("/login") //로그인 즉 인증 처리를 하는 URL을 설정합니다.
            .defaultSuccessUrl("/main") //인증성공 했을 경우 이동하는 페이지를 설정합니다.
            .usernameParameter("userId")
            .passwordParameter("userPw") //인증성공 후 별도의 처리가 필요한경우 커스텀 핸들러를 생성하여 등록할 수 있습니다.
            .successHandler { request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
                if (authentication.authorities.toString() != "[ROLE_USER]" && authentication.authorities.toString() != "[ROLE_ADMIN]") {
                    response.sendRedirect("/denied")
                } else {
                    response.sendRedirect("/main")
                }
                request.getSession().setAttribute("userId", request.getParameter("userId"))
                request.getSession().setAttribute("userPw", request.getParameter("userPw"))
            } //인증이 실패 했을 경우 이동하는 페이지를 설정합니다.
            .failureUrl("/loginForm?error")
            .permitAll() //로그인 페이지를 모두에게 허용한다.
            //로그아웃
            .and()
            .logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/logoutAction"))
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/main")
            .and()
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler {
        val accessDeniedHandler = CustomAccessDeniedHandler()
        accessDeniedHandler.setErrorPage("/denied")
        return accessDeniedHandler
    }

    //BCrypt 암호화 엔코더 빈 생성
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    //UserDetailsService 인터페이스 구현체를 설정한다.
    // - 내부의 loadUserByUsername 메소드를 통해, 로그인 인증결과를 가져온다.
//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService<UserDetailsService>(securityService).passwordEncoder(passwordEncoder())
//    }
}