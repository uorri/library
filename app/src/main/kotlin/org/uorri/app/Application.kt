package org.uorri.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = [
    "org.uorri.security",
    "org.uorri.books",
    "org.uorri.users",
])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
