package com.example.nasaapplication.ui.weather.space

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.domain.model.SpaceWeatherEntity
import com.example.nasaapplication.domain.repo.spaceweather.SpaceWeatherRepo
import com.example.nasaapplication.domain.repo.spaceweather.SpaceWeatherRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private const val EMPTY_MESSAGE = "There is no notifications for today"

class SpaceWeatherViewModel : ViewModel() {
    private var loadStateLiveDataMutable: MutableLiveData<SpaceWeatherLoadState> = MutableLiveData()
    var loadStateLiveData: LiveData<SpaceWeatherLoadState> = loadStateLiveDataMutable

    private var formattedDate = ""
    private lateinit var repo: SpaceWeatherRepo
    private lateinit var service: SpaceWeatherRetrofitService
    private lateinit var apiKey: String

    private val retrofitCallback = object : Callback<List<SpaceWeatherEntity>> {
        override fun onResponse(
            call: Call<List<SpaceWeatherEntity>>,
            response: Response<List<SpaceWeatherEntity>>
        ) {
            val body: SpaceWeatherEntity = response.body()?.get(0) ?: SpaceWeatherEntity(EMPTY_MESSAGE)
            loadStateLiveDataMutable.postValue(
                if (response.isSuccessful) {
                    SpaceWeatherLoadState.Success(body.messageType, body.messageIssueTime, body.messageBody)
                } else {
                    SpaceWeatherLoadState.Error(SpaceWeatherLoadError.SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<List<SpaceWeatherEntity>>, t: Throwable) {
            loadStateLiveDataMutable.postValue(SpaceWeatherLoadState.Error(SpaceWeatherLoadError.LOAD_ERROR))
        }
    }

    fun initViewModel(repo: SpaceWeatherRepo, service: SpaceWeatherRetrofitService, apiKey: String) {
        this.repo = repo
        this.service = service
        this.apiKey = apiKey
    }

    fun onViewCreated() = loadSpaceWeather()

    fun reloadClicked() = loadSpaceWeather()

    private fun loadSpaceWeather() {
        loadStateLiveDataMutable.value = SpaceWeatherLoadState.Loading

        val date = Calendar.getInstance().time
        formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        repo.getSpaceWeather(retrofitCallback, service, formattedDate, apiKey)
    }
}