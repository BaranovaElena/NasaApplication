package com.example.nasaapplication.ui.pod

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.R
import com.example.nasaapplication.domain.model.pod.MEDIA_TYPE_IMAGE
import com.example.nasaapplication.domain.model.pod.PodEntity
import com.example.nasaapplication.domain.repo.pod.PodRepo
import com.example.nasaapplication.domain.repo.pod.PodRepoImplRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel : ViewModel() {
    var loadStateLiveData: MutableLiveData<LoadPodState> = MutableLiveData()
    private val podRepo: PodRepo = PodRepoImplRetrofit()
    private var formattedDate = ""

    private val retrofitCallback = object : Callback<PodEntity> {
        override fun onResponse(call: Call<PodEntity>, response: Response<PodEntity>) {
            val pod: PodEntity? = response.body()
            loadStateLiveData.postValue(
                if (response.isSuccessful && pod != null) {
                    if (pod.media_type == MEDIA_TYPE_IMAGE) {
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
            loadStateLiveData.postValue(LoadPodState.Error(LoadPodError.LOAD_ERROR))
        }
    }

    fun onDayChipChanged(chipId: Int) {
        loadStateLiveData.value = LoadPodState.Loading

        val date = Calendar.getInstance().apply {
            when (chipId) {
                R.id.pod_chip_today -> {}
                R.id.pod_chip_yesterday -> add(Calendar.DATE, -1)
                R.id.pod_chip_before_yesterday -> add(Calendar.DATE, -2)
            }
        }.time
        formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        podRepo.getPictureOfTheDay(retrofitCallback, formattedDate)
    }

    fun reloadClicked() {
        loadStateLiveData.value = LoadPodState.Loading
        podRepo.getPictureOfTheDay(retrofitCallback, formattedDate)
    }
}