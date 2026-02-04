package com.inventory.inventory_config_service.models.requests

import jakarta.validation.constraints.NotBlank

data class InventoryRuleRequest(
    @field:NotBlank(message = "El nombre es requerido")
    val name: String,

    val description: String?,

    val isActive: Boolean = true
)