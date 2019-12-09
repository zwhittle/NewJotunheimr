package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ActionDao {

    /**
     * Insert an action into the database
     */
    @Insert
    fun insert(actionEntity: ActionEntity): Long

    /**
     * Update an action in the database
     */
    @Update
    fun update(actionEntity: ActionEntity)

    /**
     * Retrieve the action from the database that corresponds to the passed 'key'
     */
    @Query("SELECT * FROM action_table WHERE actionId = :key")
    fun get(key: Long): LiveData<ActionEntity>

    /**
     * Same as above but returns an ActionEntity object instead of LiveData
     */
    @Query("SELECT * FROM action_table WHERE actionId = :key")
    fun getNow(key: Long): ActionEntity

    /**
     * Retrieve all actions from the database sorted by actionId
     */
    @Query("SELECT * FROM action_table ORDER BY actionId ASC")
    fun getAllActions(): LiveData<List<ActionEntity>>

    /**
     * Retrieve all actions from the database sorted by actionId
     * Requires being called from a background thread
     */
    @Query("SELECT * FROM action_table ORDER BY actionId ASC")
    fun getAllActionsNow(): List<ActionEntity>

    /**
     * Retrieve the first action from the database when sorted by actionId
     */
    @Query("SELECT * FROM action_table ORDER BY actionId ASC LIMIT 1")
    fun getFirstAction(): LiveData<ActionEntity>

    /**
     * Delete a single action from the database
     */
    @Delete
    fun delete(action: ActionEntity)

    /**
     * Delete all actions from the database
     */
    @Query("DELETE FROM action_table")
    fun clear()
}