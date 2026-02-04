package com.inventory.inventory_config_service.mappers

import com.inventory.inventory_config_service.models.entities.InventoryRule
import com.inventory.inventory_config_service.models.requests.InventoryRuleRequest
import com.inventory.inventory_config_service.models.responses.InventoryRuleResponse
import org.springframework.stereotype.Component

@Component
class InventoryRuleMapper {


    fun toResponse(entity: InventoryRule): InventoryRuleResponse {
        return InventoryRuleResponse(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            isActive = entity.isActive,
            updatedBy = entity.updatedBy,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }


    fun toEntity(request: InventoryRuleRequest, userId: String): InventoryRule {
        return InventoryRule(
            name = request.name,
            description = request.description,
            isActive = request.isActive,
            updatedBy = userId
        )
    }


    fun updateEntity(entity: InventoryRule, request: InventoryRuleRequest, userId: String) {
        entity.name = request.name
        entity.description = request.description
        entity.isActive = request.isActive
        entity.updatedBy = userId

    }
}