package com.awesome.zach.newjotunheimr

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TagEntity represents an assignable attribute for actions in the database
 */
@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0L,

    var name: String)

/**
 * Map TagEntities to domain Tags
 */
fun List<TagEntity>.asDomainModel(): List<Tag> {
    return map {
        Tag(tagId = it.tagId,
            name = it.name,
            actions = null)
    }
}

/**
 * Map single TagEntity to domain Tag
 */
fun TagEntity.asDomainModel(): Tag {
    return Tag(tagId = this.tagId,
               name = this.name,
               actions = null)
}