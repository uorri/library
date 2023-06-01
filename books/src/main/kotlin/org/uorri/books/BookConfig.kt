package org.uorri.books

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = ["org.uorri.common"])
class BookConfig