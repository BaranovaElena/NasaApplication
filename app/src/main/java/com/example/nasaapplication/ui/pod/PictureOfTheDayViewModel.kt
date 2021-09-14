package com.example.nasaapplication.ui.pod

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.domain.model.pod.MEDIA_TYPE_IMAGE
import com.example.nasaapplication.domain.model.pod.PodEntity
import com.example.nasaapplication.domain.repo.pod.PodRepo
import com.example.nasaapplication.domain.repo.pod.PodRepoImplRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel : ViewModel() {
    var loadStateLiveData: MutableLiveData<LoadPodState> = MutableLiveData()
    private val podRepo: PodRepo = PodRepoImplRetrofit()

    fun onViewCreated() {
        loadStateLiveData.value = LoadPodState.Loading

        val retrofitCallback = object : Callback<PodEntity> {
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

        podRepo.getPictureOfTheDay(retrofitCallback)

    }
}