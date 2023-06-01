package org.uorri.users.service

import org.uorri.common.dto.LoginCredentials
import org.uorri.common.entity.User
import org.uorri.common.dto.UserDetails
import org.uorri.common.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {

    fun getAllUsers(): Flux<User>
    fun getUserById(id: Long): Mono<User>
    fun createUser(userDetails: Mono<UserDetails>): Mono<User>
    fun getUser(loginCredentials: LoginCredentials) : Mono<User>
    fun getInfoByUser(login: String) : Mono<UserDto>
    fun deleteById(id: Long, roleId: Int): Mono<Void>
}