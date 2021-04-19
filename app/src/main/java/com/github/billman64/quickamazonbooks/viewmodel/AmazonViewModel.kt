package com.github.billman64.quickamazonbooks.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.billman64.quickamazonbooks.model.data.AmazonBooksResponse
import com.github.billman64.quickamazonbooks.model.data.AmazonBooksResponseItem
import com.github.billman64.quickamazonbooks.model.network.AmazonRetrofitInstance
import com.github.billman64.quickamazonbooks.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AmazonViewModel: ViewModel() {

    private var networkJob: Job?= null

    private val bookLiveData: MutableLiveData<List<AmazonBooksResponseItem>> = MutableLiveData()
    val stateLiveData: MutableLiveData<State> = MutableLiveData()

    private val amazonRetrofitInstance = AmazonRetrofitInstance()

    fun getBooks(): LiveData<List<AmazonBooksResponseItem>> {
        stateLiveData.value = State.LOADING

        networkJob = viewModelScope.launch(Dispatchers.IO) {

            try {
                val bookResponse = amazonRetrofitInstance.getBooks().await()
                bookResponse?.let {
                    bookLiveData.postValue(it)
                    stateLiveData.postValue(State.COMPLETED)
                } ?: stateLiveData.postValue(State.ERROR)

            } catch (e: Exception){
                stateLiveData.postValue(State.ERROR)
                Log.e("TAG_Error", e.toString())
            }
        }
        return bookLiveData
    }

    override fun onCleared() {
        networkJob?.cancel()
        super.onCleared()
    }


}