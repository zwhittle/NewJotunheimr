package com.awesome.zach.newjotunheimr

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ActionEntity represents an action in the database
 */
@Entity(tableName = "action_table")
data class ActionEntity(
    @PrimaryKey(autoGenerate = true)
    var actionId: Long = 0L,

    var name: String,
    var body: String,

    @ColumnInfo(name = "created_timestamp")
    var createdTimestamp: Long = System.currentTimeMillis(),

    var completed: Boolean = false)

/**
 * Map ActionEntities to domain Actions
 */
fun List<ActionEntity>.asDomainModel(): List<Action> {
    return map {
        Action(actionId = it.actionId,
               name = it.name,
               body = it.body,
               createdTimestamp = it.createdTimestamp,
               completed = it.completed,
               tags = null)
    }
}

/**
 * Map single ActionEntity to domain Action
 */
fun ActionEntity.asDomainModel(): Action {
    return Action(actionId = this.actionId,
                  name = this.name,
                  body = this.body,
                  createdTimestamp = this.createdTimestamp,
                  completed = this.completed,
                  tags = null)
}