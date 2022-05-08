package dev.vladyour.tset.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ValidationExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handle(e: IllegalArgumentException, request: WebRequest): ResponseEntity<ApiError> {
        return ResponseEntity.badRequest().body(ApiError(e.message))
    }
}
