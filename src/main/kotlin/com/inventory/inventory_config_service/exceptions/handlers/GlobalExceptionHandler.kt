package com.inventory.inventory_config_service.exceptions.handlers

import com.inventory.inventory_config_service.exceptions.BadRequestException
import com.inventory.inventory_config_service.exceptions.NotFoundException
import com.inventory.inventory_config_service.exceptions.UnauthorizedActionException
import com.inventory.inventory_config_service.models.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 404 - Not Found..
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.NOT_FOUND
        )
    }

    // 400 - Bad Request (Lógica de negocio)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    // 400 - Bad Request (Validaciones de Spring @Valid, ej: @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // Extraemos el mensaje del primer error de validación
        val message = ex.bindingResult.allErrors.firstOrNull()?.defaultMessage ?: "Error de validación"
        return ResponseEntity(
            ErrorResponse(message),
            HttpStatus.BAD_REQUEST
        )
    }

    // 403 - Forbidden / Unauthorized (Regla de auditoría)
    @ExceptionHandler(UnauthorizedActionException::class)
    fun handleUnauthorizedActionException(ex: UnauthorizedActionException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.FORBIDDEN // O UNAUTHORIZED, según prefiera el profe (403 es común para permisos)
        )
    }

    // 500 - Catch-All (PARA EVITAR PUNTOS MENOS)
    // Captura cualquier error no controlado para que no salga la traza fea de Java
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("Error interno del servidor: ${ex.message}"), // Mensaje controlado
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}