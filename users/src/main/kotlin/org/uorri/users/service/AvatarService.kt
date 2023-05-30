package org.uorri.users.service

import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono
import java.io.File

interface AvatarService {

    fun saveAvatar(filePartMono: Mono<FilePart>, login: String) : Mono<Void>
    fun getAvatar(login: String) : File

}