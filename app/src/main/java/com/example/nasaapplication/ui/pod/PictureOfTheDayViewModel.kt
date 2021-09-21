package com.example.nasaapplication.ui.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.NasaApplication
import com.example.nasaapplication.domain.model.pod.MEDIA_TYPE_IMAGE
import com.example.nasaapplication.domain.model.pod.PodEntity
import com.example.nasaapplication.domain.repo.pod.PodRepo
import com.example.nasaapplication.domain.repo.pod.PodRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel : ViewModel() {
    private var loadStateLiveDataMutable: MutableLiveData<LoadPodState> = MutableLiveData()
    var loadStateLiveData: LiveData<LoadPodState> = loadStateLiveDataMutable

    private var formattedDate = ""
    private lateinit var repo: PodRepo
    private lateinit var service: PodRetrofitService
    private lateinit var apiKey: String

    private val retrofitCallback = object : Callback<PodEntity> {
        override fun onResponse(call: Call<PodEntity>, response: Response<PodEntity>) {
            val pod: PodEntity? = response.body()
            loadStateLiveDataMutable.postValue(
                if (response.isSuccessful && pod != null) {
                    if (pod.mediaType == MEDIA_TYPE_IMAGE) {
                        LoadPodState.Success(pod.title, pod.url, pod.explanation)
                    } else {
                        LoadPodState.Error(LoadPodError.NOT_IMAGE_ERROR)
                    }
                } else {
                    LoadPodState.Error(LoadPodError.SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<PodEntity>, t: Throwable) {
            loadStateLiveDataMutable.postValue(LoadPodState.Error(LoadPodError.LOAD_ERROR))
        }
    }

    fun initViewModel(repo: PodRepo, service: PodRetrofitService, apiKey: String){
        this.repo = repo
        this.service = service
        this.apiKey = apiKey
    }

    fun onDayChipChanged(day: Int) {
        loadStateLiveDataMutable.value = LoadPodState.Loading

        val date = Calendar.getInstance().apply {
            when (day) {
                Days.TODAY -> {}
                Days.YESTERDAY -> add(Calendar.DATE, -1)
                Days.DAY_BEFORE_YESTERDAY -> add(Calendar.DATE, -2)
            }
        }.time
        formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        repo.getPictureOfTheDay(retrofitCallback, service, formattedDate, apiKey)
    }

    fun reloadClicked() {
        loadStateLiveDataMutable.value = LoadPodState.Loading
        repo.getPictureOfTheDay(retrofitCallback, service, formattedDate, apiKey)
    }
}