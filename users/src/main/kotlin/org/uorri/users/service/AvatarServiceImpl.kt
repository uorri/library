package org.uorri.users.service

import org.springframework.core.io.FileSystemResource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class AvatarServiceImpl : AvatarService {

    val avatarsPath: Path = Paths.get("api/src/main/resources/avatars")
    val avatarExtension = "png"

    override fun saveAvatar(filePartMono: Mono<FilePart>, login: String): Mono<Void> {
        return filePartMono
            .filter {
                it.filename().substringAfterLast(".", "") == avatarExtension }
            .flatMap {
                it.transferTo(avatarsPath.resolve(login.plus(".$avatarExtension")))
            }.then()
    }

    override fun getAvatar(login: String): File {
        val imageResource = FileSystemResource("$avatarsPath.$avatarExtension")
        return imageResource.file
    }

}