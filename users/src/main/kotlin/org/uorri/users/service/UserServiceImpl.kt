package org.uorri.users.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.uorri.common.dto.LoginCredentials
import org.uorri.common.entity.User
import org.uorri.common.dto.UserDetails
import org.uorri.common.dto.UserDto
import org.uorri.users.repository.UserRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    @Autowired val userRepository: UserRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) : UserService {

    private val userRoleId = 1
    override fun getAllUsers(): Flux<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): Mono<User> {
        return userRepository.findById(id)
    }

    @Transactional
    override fun createUser(userDetails: Mono<UserDetails>): Mono<User> {
        return userDetails.map { it.toEntity() }.flatMap { user -> userRepository.save(user) }
    }

    private fun UserDetails.toEntity() = User(
        firstName = firstName,
        lastName = lastName,
        login = login,
        password = passwordEncoder.encode(password),
        roleId = userRoleId
    )

    override fun getUser(loginCredentials: LoginCredentials): Mono<User> {
        return userRepository.findByLogin(loginCredentials.login)
    }

    override fun getInfoByUser(login: String): Mono<UserDto> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long, roleId: Int): Mono<Void> {
        require(roleId == 2)
        return userRepository.deleteById(id)
    }

}