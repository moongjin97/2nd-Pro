package com.insta.enumeration

import lombok.Getter

@Getter
enum class UserRole(val value: String) {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private var roleValue: String? = null
    open fun UserRole(value: String) {
        this.roleValue = value
    }
}