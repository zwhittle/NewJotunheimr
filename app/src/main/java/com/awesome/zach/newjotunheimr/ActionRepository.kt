package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ActionRepository(private val database: ActionDatabase,
                       private val scope: CoroutineScope) {

    /**
     * list of actions to be displayed in the UI
     */
    val actions= getActionsAsModels()

    /**
     * single action to be displayed in the UI
     */
    val action = getActionAsModel()

    /**
     * gets the actions from the dao
     * retrieves the tags for each of them
     * converts to domain model
     */
    fun getActionsAsModels() : LiveData<List<Action>> {

        val allActionsFromDao = database.actionDao.getAllActions() // retrieve all the actions in the database

        // in order to iterate through a LiveData<List<>>, you use Transformations.map in lieu of foreach {}
        return Transformations.map(allActionsFromDao) { actionList ->

            val newList = arrayListOf<Action>()

            actionList.forEach { action ->
                val tags = database.actionTagDao.getTagsForAction(action.actionId)
                val domTags = Transformations.map(tags) {
                    it.asDomainModel()
                }

                val domAction = action.asDomainModel()
                domAction.tags = domTags
                newList.add(domAction)
            }

            newList.toList()
        }
    }

    /**
     * same as above but for a single Action
     */
    fun getActionAsModel(): LiveData<Action> {
        val firstAction = database.actionDao.getFirstAction()

        return Transformations.map(firstAction) { action ->
            val tags = database.actionTagDao.getTagsForAction(action.actionId)
            val domTags = Transformations.map(tags) {
                it.asDomainModel()
            }

            val domAction = action.asDomainModel()
            domAction.tags = domTags

            domAction
        }
    }

    /**
     * insert action into database
     */
    fun newAsync(action: Action): Deferred<Action> {
        return scope.async (Dispatchers.IO) {
            val actionMap = action.asDatabaseEntity()
            val actionEntity = actionMap["action"] as ActionEntity
            val tags = actionMap["tags"] as List<TagEntity>

            tags.forEach { tag ->
                val actionTag = ActionTagEntity(actionEntity.actionId, tag.tagId)
                database.actionTagDao.insert(actionTag)
            }

            val id = database.actionDao.insert(actionEntity)
            return@async database.actionDao.getNow(id).asDomainModel()
        }
    }

    /**
     * Check if a given tag exists in the database
     */
//    fun tagExistsAsync(tag: Tag): Boolean {
//        scope.launch(Dispatchers.IO) {
//            val eTag = database.tagDao.get(tag.tagId)
//            // TODO implement this check to see if a tag exists in the db before creating
//        }
//    }

    /**
     * update action in the database
     */
//    fun updateAsync(action: Action): Deferred<Action> {
//        return scope.async(Dispatchers.IO) {
//            val actionMap = action.as
//        }
//    }
}