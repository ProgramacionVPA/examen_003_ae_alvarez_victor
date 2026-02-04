package com.inventory.inventory_config_service.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "inventory_rules")
class InventoryRule(

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var isActive: Boolean = true,


    @Column(name = "updated_by", nullable = false)
    var updatedBy: String

) : BaseEntity()