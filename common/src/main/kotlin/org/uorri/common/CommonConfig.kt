package org.uorri.common

import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig(val connectionFactory: ConnectionFactory) {

    @Bean
    fun jooqDSLContext(): DSLContext {
        return DSL.using(connectionFactory).dsl()
    }

}