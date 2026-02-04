package com.inventory.inventory_config_service.controllers

import com.inventory.inventory_config_service.models.requests.InventoryRuleRequest
import com.inventory.inventory_config_service.models.responses.InventoryRuleResponse
import com.inventory.inventory_config_service.services.InventoryRuleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/rules")
class InventoryRuleController(
    private val service: InventoryRuleService
) {

    private fun getUserIdFromToken(jwt: Jwt): String {

        return jwt.claims["username"] as? String ?: jwt.claims["sub"] as? String ?: "unknown"
    }


    @GetMapping
    fun getAll(): ResponseEntity<List<InventoryRuleResponse>> {
        return ResponseEntity.ok(service.getAllRules())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<InventoryRuleResponse> {
        return ResponseEntity.ok(service.getRuleById(id))
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin')") // <--- Aquí está el ajuste para tu grupo 'admin'
    fun create(
        @Valid @RequestBody request: InventoryRuleRequest,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<InventoryRuleResponse> {
        val userId = getUserIdFromToken(jwt)
        val created = service.createRule(request, userId)
        return ResponseEntity.created(URI.create("/api/rules/${created.id}")).body(created)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: InventoryRuleRequest,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<InventoryRuleResponse> {
        val userId = getUserIdFromToken(jwt)
        return ResponseEntity.ok(service.updateRule(id, request, userId))
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun toggle(
        @PathVariable id: Long,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<InventoryRuleResponse> {
        val userId = getUserIdFromToken(jwt)
        return ResponseEntity.ok(service.toggleStatus(id, userId))
    }
}