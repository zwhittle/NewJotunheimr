package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TagRepository(private val database: ActionDatabase,
                    private val scope: CoroutineScope) {

    /**
     * list of tags to be displayed in the UI
     */
    val tags = getTagsAsModels()

    /**
     * single tag to be displayed in the UI
     */
    val tagModel: LiveData<Tag> = getTagAsModel()

    /**
     * gets the tags from the dao
     * retrieves the actions for each of them
     * converts to domain model
     */
    fun getTagsAsModels(): LiveData<List<Tag>> {
        val allTagsFromDao = database.tagDao.getAllTags()

        return Transformations.map(allTagsFromDao) { tagList ->

            val newList = arrayListOf<Tag>()

            tagList.forEach { tag ->
                val actions = database.actionTagDao.getActionsForTag(tag.tagId)
                val domActions = Transformations.map(actions) {
                    it.asDomainModel()
                }

                val domTag = tag.asDomainModel()
                domTag.actions = domActions
                newList.add(domTag)
            }

            newList.toList()
        }
    }

    /**
     * same as above but for a sing tag
     */
    fun getTagAsModel(): LiveData<Tag> {
        val firstTag = database.tagDao.getFirstTag()

        return Transformations.map(firstTag) {tag ->
            val actions = database.actionTagDao.getActionsForTag(tag.tagId)
            val domActions = Transformations.map(actions) {
                it.asDomainModel()
            }

            val domTag = tag.asDomainModel()
            domTag.actions = domActions

            domTag
        }
    }

    /**
     * insert tag into database
     */
    suspend fun newAsync(tag: Tag): Deferred<Tag> {
        return scope.async (Dispatchers.IO) {
            val id = database.tagDao.insert(tag.asDatabaseEntity())
            return@async database.tagDao.getNow(id).asDomainModel()
        }
    }
}