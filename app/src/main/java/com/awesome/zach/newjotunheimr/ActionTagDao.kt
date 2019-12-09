package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActionTagDao {

    @Insert
    fun insert(actionTagEntity: ActionTagEntity)

    @Query("SELECT * FROM action_table INNER JOIN actiontag ON action_table.actionId = actiontag.actionId WHERE actiontag.tagId = :tagId")
    fun getActionsForTag(tagId: Long): LiveData<List<ActionEntity>>

    @Query("SELECT * FROM tag INNER JOIN actiontag ON tag.tagId = actiontag.tagId WHERE actiontag.actionId = :actionId")
    fun getTagsForAction(actionId: Long): LiveData<List<TagEntity>>
}