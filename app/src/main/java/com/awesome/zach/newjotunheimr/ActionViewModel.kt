package com.awesome.zach.newjotunheimr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActionViewModel(application: Application) : AndroidViewModel(application) {

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

    private val actionRepository = ActionRepository(getDatabase(application), uiScope)

    /**
     * A single action logged by the user
     */
    val action = actionRepository.getActionAsModel()

    /**
     * A list of the actions logged by the user
     */
    val actionList = actionRepository.getActionsAsModels()

    /**
     * Helper function to simplify the call to get the db instance
     */
    private fun getDatabase(application: Application) = ActionDatabase.getInstance(application)

    fun insert(action: Action) {
        uiScope.launch(Dispatchers.IO) {
            val newAction = newAsync(action)

        }
    }

    private suspend fun newAsync(action: Action) = actionRepository.newAsync(action)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}