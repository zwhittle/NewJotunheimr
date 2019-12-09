package com.awesome.zach.newjotunheimr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TagViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [MainViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val tagRepository = TagRepository(getDatabase(application), uiScope)

    /**
     * A single tag created by the user
     */
    val tagModel = tagRepository.getTagAsModel()

    /**
     * A list of the tags created by the user
     */
    val tagList = tagRepository.getTagsAsModels()

    /**
     * Helper function to simplify the call to get the db instance
     */
    private fun getDatabase(application: Application) = ActionDatabase.getInstance(application)

    fun insert(tag: Tag) {
        uiScope.launch {
            val newTag = newAsync(tag)
        }
    }

    private suspend fun newAsync(tag: Tag) = tagRepository.newAsync(tag)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}