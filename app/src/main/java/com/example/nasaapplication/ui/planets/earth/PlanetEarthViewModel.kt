package com.example.nasaapplication.ui.planets.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.domain.model.EarthPhotoEntity
import com.example.nasaapplication.domain.repo.planetearth.EarthPhotoRepo
import com.example.nasaapplication.domain.repo.planetearth.EarthPhotoRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val EMPTY_MESSAGE = "There is no notifications for today"
private const val IMAGE_PATH_BASE_URL = "https://api.nasa.gov/EPIC/archive/natural/"

class PlanetEarthViewModel : ViewModel() {
    private var loadStateLiveDataMutable: MutableLiveData<EarthPictureLoadState> = MutableLiveData()
    var loadStateLiveData: LiveData<EarthPictureLoadState> = loadStateLiveDataMutable

    private lateinit var repo: EarthPhotoRepo
    private lateinit var service: EarthPhotoRetrofitService
    private lateinit var apiKey: String

    private val retrofitCallback = object : Callback<List<EarthPhotoEntity>> {
        override fun onResponse(
            call: Call<List<EarthPhotoEntity>>,
            response: Response<List<EarthPhotoEntity>>
        ) {
            val body: EarthPhotoEntity =
                response.body()?.get(0) ?: EarthPhotoEntity("", EMPTY_MESSAGE)
            loadStateLiveDataMutable.postValue(
                if (response.isSuccessful) {
                    EarthPictureLoadState.Success(
                        body.caption,
                        body.date,
                        getImagePath(body.image, body.date)
                    )
                } else {
                    EarthPictureLoadState.Error(EarthPictureLoadError.SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<List<EarthPhotoEntity>>, t: Throwable) {
            loadStateLiveDataMutable.postValue(EarthPictureLoadState.Error(EarthPictureLoadError.LOAD_ERROR))
        }
    }

    private fun getImagePath(image: String, date: String?): String {
        val dateRequest = date?.substring(0, 10)?.replace("-", "/") ?: ""
        return "${IMAGE_PATH_BASE_URL}${dateRequest}/png/${image}.png?api_key=${apiKey}"
    }

    fun initViewModel(repo: EarthPhotoRepo, service: EarthPhotoRetrofitService, apiKey: String) {
        this.repo = repo
        this.service = service
        this.apiKey = apiKey
    }

    fun onViewCreated() = loadEarthPhoto()
    fun reloadClicked() = loadEarthPhoto()

    private fun loadEarthPhoto() {
        loadStateLiveDataMutable.value = EarthPictureLoadState.Loading
        repo.getEarthPhoto(retrofitCallback, service, apiKey)
    }
}