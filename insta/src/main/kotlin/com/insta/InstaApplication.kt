package com.insta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InstaApplication

fun main(args: Array<String>) {
	runApplication<InstaApplication>(*args)
}
