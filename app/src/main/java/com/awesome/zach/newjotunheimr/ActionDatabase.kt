package com.awesome.zach.newjotunheimr

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActionEntity::class, TagEntity::class, ActionTagEntity::class],
          version = 1,
          exportSchema = false)
abstract class ActionDatabase : RoomDatabase() {

    /**
     * Dao references
     */
    abstract val actionDao: ActionDao
    abstract val tagDao: TagDao
    abstract val actionTagDao: ActionTagDao

    /**
     * Singleton instance of the database
     */
    companion object {

        @Volatile
        private var INSTANCE: ActionDatabase? = null

        fun getInstance(context: Context): ActionDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    ActionDatabase::class.java,
                                                    "action_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}