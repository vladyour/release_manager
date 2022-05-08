package dev.vladyour.tset.exception

import org.springframework.http.HttpStatus

data class ApiError(
    val message: String?,
    val status: HttpStatus = HttpStatus.BAD_REQUEST
)
