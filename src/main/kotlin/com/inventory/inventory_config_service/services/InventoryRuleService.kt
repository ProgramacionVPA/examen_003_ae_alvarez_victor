package com.inventory.inventory_config_service.services

import com.inventory.inventory_config_service.exceptions.NotFoundException
import com.inventory.inventory_config_service.mappers.InventoryRuleMapper
import com.inventory.inventory_config_service.models.requests.InventoryRuleRequest
import com.inventory.inventory_config_service.models.responses.InventoryRuleResponse
import com.inventory.inventory_config_service.repositories.InventoryRuleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryRuleService(
    private val repository: InventoryRuleRepository,
    private val mapper: InventoryRuleMapper
) {

    fun getAllRules(): List<InventoryRuleResponse> {
        return repository.findAll().map { mapper.toResponse(it) }
    }

    fun getRuleById(id: Long): InventoryRuleResponse {
        val rule = repository.findById(id)
            .orElseThrow { NotFoundException("Regla no encontrada con id: $id") }
        return mapper.toResponse(rule)
    }

    @Transactional
    fun createRule(request: InventoryRuleRequest, userId: String): InventoryRuleResponse {
        val entity = mapper.toEntity(request, userId)
        val saved = repository.save(entity)
        return mapper.toResponse(saved)
    }

    @Transactional
    fun updateRule(id: Long, request: InventoryRuleRequest, userId: String): InventoryRuleResponse {
        val existingRule = repository.findById(id)
            .orElseThrow { NotFoundException("Regla no encontrada con id: $id") }

        mapper.updateEntity(existingRule, request, userId)

        val saved = repository.save(existingRule)
        return mapper.toResponse(saved)
    }


    @Transactional
    fun toggleStatus(id: Long, userId: String): InventoryRuleResponse {
        val existingRule = repository.findById(id)
            .orElseThrow { NotFoundException("Regla no encontrada con id: $id") }

        existingRule.isActive = !existingRule.isActive
        existingRule.updatedBy = userId

        val saved = repository.save(existingRule)
        return mapper.toResponse(saved)
    }
}