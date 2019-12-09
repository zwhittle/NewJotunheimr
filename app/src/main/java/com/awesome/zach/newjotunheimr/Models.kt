package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * Action represents an action in the UI
 */
data class Action(
    val actionId: Long,
    var name: String,
    var body: String,
    val createdTimestamp: Long,
    var completed: Boolean,
    var tags: LiveData<List<Tag>>?
) {

    fun asDatabaseEntity(): Map<String, Any?> {
        val nTags = tags
        var tagEntities: List<TagEntity>? = listOf<TagEntity>()


        if (nTags != null) {
            tagEntities = Transformations.map(tags!!) { tagList ->
                val newList = arrayListOf<TagEntity>()

                tagList.forEach {
                    newList.add(it.asDatabaseEntity())
                }

                newList.toList()

            }.value
        } else {
            tagEntities = null
        }

        val actionEntity = ActionEntity(actionId, name, body, createdTimestamp, completed)

        return mapOf("action" to actionEntity, "tag" to tagEntities)
    }
}

/**
 * Tag represents an attribute assignable to an action
 */
data class Tag(
    val tagId: Long,
    var name: String,
    var actions: LiveData<List<Action>>?
) {

    fun asDatabaseEntity(): TagEntity {
        return TagEntity(tagId, name)
    }
}