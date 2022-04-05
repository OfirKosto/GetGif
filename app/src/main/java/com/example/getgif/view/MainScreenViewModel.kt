package com.example.getgif.view

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getgif.R
import com.example.getgif.model.GifsRepository
import com.example.getgif.model.dataclasses.DataObject
import com.example.getgif.model.dataclasses.DataResult
import com.example.getgif.model.dataclasses.Gif
import com.example.getgif.model.interfaces.IResponseListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainScreenViewModel : ViewModel() {

    private val _gifsList: MutableLiveData<List<DataObject>> = MutableLiveData()

    val gifsList: LiveData<List<DataObject>> get() = _gifsList

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()

    val errorLiveData: LiveData<String> get() = _errorLiveData

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs(){
        viewModelScope.launch {
            val response =
                try {
                    GifsRepository.getTrendingGifs()
                }
                catch (ex: IOException)
                {
                    _errorLiveData.postValue(ex.message)
                    return@launch
                }
                catch (ex: HttpException)
                {
                    _errorLiveData.postValue(ex.message())
                    return@launch
                }

            if(response.isSuccessful && response.body()!= null)
            {
                    _gifsList.postValue(response.body()!!.dataObjectList)
            }
            else
            {
                _errorLiveData.postValue(Resources.getSystem().getString(R.string.error_get_data_msg))
            }
        }
    }

    fun getGifsByName(name: String) {
        viewModelScope.launch {
            val response =
                try {
                    GifsRepository.getGifsByName(name)
                }
                catch (ex: IOException)
                {
                    _errorLiveData.postValue(ex.message)
                    return@launch
                }
                catch (ex: HttpException)
                {
                    _errorLiveData.postValue(ex.message())
                    return@launch
                }

                if(response.isSuccessful && response.body()!= null)
                {
                        _gifsList.postValue(response.body()!!.dataObjectList)
                }
                else
                {
                    _errorLiveData.postValue(Resources.getSystem().getString(R.string.error_get_data_msg))
                }
        }
    }

//    fun getTrendingGifs(){
//        viewModelScope.launch {
//            try {
//                GifsRepository.getTrendingGifs(object : IResponseListener{
//                    override fun getResponse(gifsList: List<Gif>?, isSuccessful: Boolean) {
//                        if(isSuccessful)
//                        {
//                            if(gifsList != null)
//                            {
//                                _gifsList.postValue(gifsList!!)
//                            }
//                        }
//                        else
//                        {
//
//                            _errorLiveData.postValue(Resources.getSystem().getString(R.string.error_get_data_msg))
//                        }
//                    }
//                })
//            }
//            catch (ex: IOException)
//            {
//                _errorLiveData.postValue(ex.message)
//                return@launch
//            }
//            catch (ex: HttpException)
//            {
//                _errorLiveData.postValue(ex.message())
//                return@launch
//            }
//        }
//    }


}