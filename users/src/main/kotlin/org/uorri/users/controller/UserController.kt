package org.uorri.users.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.uorri.common.dto.LoginCredentials
import org.uorri.common.dto.UserDetails
import org.uorri.common.entity.User
import org.uorri.security.config.AuthResponse
import org.uorri.security.config.JwtService
import org.uorri.users.service.AvatarService
import org.uorri.users.service.UserService
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/users")
class UserController(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val avatarService: AvatarService
) {
    @PostMapping("/avatar")
    fun setAvatar(
        @RequestHeader("Authorization") token: String,
        @RequestPart(value = "file", required = false) filePartMono: Mono<FilePart>
    ): Mono<Void> {
        val login = jwtService.getLoginByToken(token)
        return avatarService.saveAvatar(filePartMono, login)
    }

    @PostMapping("/register")
    fun registration(@RequestBody userDetails: Mono<UserDetails>): Mono<ResponseEntity<AuthResponse>> {
        val user: Mono<User> = userService.createUser(userDetails)
        return jwtService.getAuthResponse(user).map { ResponseEntity.ok(it) }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginCredentials: LoginCredentials): Mono<ResponseEntity<AuthResponse>> {
        val user: Mono<User> = userService.getUser(loginCredentials)
        return jwtService.getAuthResponse(loginCredentials, user).map { ResponseEntity.ok(it) }
    }

    @GetMapping("/avatar", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getAvatar(@RequestHeader("Authorization") token: String): ByteArray {
        val login = jwtService.getLoginByToken(token)
        val avatarFile = avatarService.getAvatar(login)
        return avatarFile.readBytes()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable("id") id: Long,
        @RequestHeader("Authorization") token: String
    ) {
        val roleId = jwtService.getRoleId(token)
        userService.deleteById(id, roleId)
    }

}