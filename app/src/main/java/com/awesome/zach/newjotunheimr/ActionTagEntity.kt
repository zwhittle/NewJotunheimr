package com.awesome.zach.newjotunheimr

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * ActionTagEntity is an association table to store the assignments of tags to actions
 */
@Entity(tableName = "actiontag",
        primaryKeys = ["actionId", "tagId"],
        foreignKeys = [
            ForeignKey(entity = ActionEntity::class,
                       parentColumns = ["actionId"],
                       childColumns = ["actionId"]),
            ForeignKey(entity = TagEntity::class,
                       parentColumns = ["tagId"],
                       childColumns = ["tagId"])])
data class ActionTagEntity(
    val actionId: Long,
    val tagId: Long)