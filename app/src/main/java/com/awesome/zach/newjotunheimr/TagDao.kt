package com.awesome.zach.newjotunheimr

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {

    /**
     * Insert a tag into the database
     */
    @Insert
    fun insert(tagEntity: TagEntity): Long

    /**
     * Update a tag in the database
     */
    @Update
    fun updated(tagEntity: TagEntity)

    /**
     * Retrieve the tag from the database that corresponds to the passed 'key'
     */
    @Query("SELECT * FROM tag WHERE tagId = :key")
    fun get(key: Long): LiveData<TagEntity>

    /**
     * Same as above but returns a TagEntity object instead of LiveData
     */
    @Query("SELECT * FROM tag WHERE tagId = :key")
    fun getNow(key: Long): TagEntity

    /**
     * Retrieve all tags from the database sorted by tagId
     */
    @Query("SELECT * FROM tag ORDER BY tagId ASC")
    fun getAllTags(): LiveData<List<TagEntity>>

    /**
     * Retrieve all tags from the database sorted by tagId
     */
    @Query("SELECT * FROM tag ORDER BY tagId ASC")
    fun getAllTagsNow(): List<TagEntity>

    /**
     * Retrieve the first tag from the database when sorted by tagId
     */
    @Query("SELECT * FROM tag ORDER BY tagId ASC LIMIT 1")
    fun getFirstTag(): LiveData<TagEntity>

    /**
     * Delete a single tag from the database
     */
    @Delete
    fun delete(tag: TagEntity)

    /**
     * Delete all tags from the database
     */
    @Query("DELETE FROM tag")
    fun clear()
}