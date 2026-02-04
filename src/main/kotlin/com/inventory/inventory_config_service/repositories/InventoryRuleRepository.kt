package com.inventory.inventory_config_service.repositories

import com.inventory.inventory_config_service.models.entities.InventoryRule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRuleRepository : JpaRepository<InventoryRule, Long> {

    fun findByIsActiveTrue(): List<InventoryRule>


    fun findByName(name: String): InventoryRule?
}