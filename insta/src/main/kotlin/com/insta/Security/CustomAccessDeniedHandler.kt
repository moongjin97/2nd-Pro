package com.insta.Security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler : AccessDeniedHandler {
    private var errorPage: String? = null
    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: AccessDeniedException
    ) {
        val deniedUrl = errorPage + "?exception=" + e.message
        response.sendRedirect(deniedUrl)
    }

    fun setErrorPage(errorPage: String?) {
        this.errorPage = errorPage
    }
}