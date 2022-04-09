package com.example.getgif.view.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getgif.R
import com.example.getgif.model.repositories.GifsRepository
import com.example.getgif.model.dataclasses.DataObject
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainScreenViewModel : ViewModel() {

    private val _gifsList: MutableLiveData<List<DataObject>> = MutableLiveData()

    val gifsList: LiveData<List<DataObject>> get() = _gifsList

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()

    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getTrendingGifs(viewRoot: View){
        viewModelScope.launch {
            val response =
                try {
                    GifsRepository.getTrendingGifs()
                }
                catch (ex: IOException)
                {
                    _errorLiveData.postValue(viewRoot.resources.getString(R.string.io_exeption_msg))
                    return@launch
                }
                catch (ex: HttpException)
                {
                    _errorLiveData.postValue(viewRoot.resources.getString(R.string.http_exeption_msg))
                    return@launch
                }

            if(response.isSuccessful && response.body()!= null)
            {
                    _gifsList.postValue(response.body()!!.dataObjectList)
            }
        }
    }

    fun getGifsByName(viewRoot: View, name: String) {
        viewModelScope.launch {
            val response =
                try {
                    GifsRepository.getGifsByName(name)
                }
                catch (ex: IOException)
                {
                    _errorLiveData.postValue(viewRoot.resources.getString(R.string.io_exeption_msg))
                    return@launch
                }
                catch (ex: HttpException)
                {
                    _errorLiveData.postValue(viewRoot.resources.getString(R.string.http_exeption_msg))
                    return@launch
                }

                if(response.isSuccessful && response.body()!= null)
                {
                        _gifsList.postValue(response.body()!!.dataObjectList)
                }
        }
    }
}