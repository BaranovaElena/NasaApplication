package com.example.nasaapplication.ui.planets.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapplication.domain.model.MarsPhotoEntity
import com.example.nasaapplication.domain.model.MarsResponseEntity
import com.example.nasaapplication.domain.repo.planetmars.MarsPhotoRepo
import com.example.nasaapplication.domain.repo.planetmars.MarsPhotoRetrofitService
import com.example.nasaapplication.ui.LoadError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private const val EMPTY_MESSAGE = "There is no photos for today"

class PlanetMarsViewModel : ViewModel() {
    private var loadStateLiveDataMutable: MutableLiveData<MarsPictureLoadState> = MutableLiveData()
    var loadStateLiveData: LiveData<MarsPictureLoadState> = loadStateLiveDataMutable

    private var formattedDate = ""
    private lateinit var repo: MarsPhotoRepo
    private lateinit var service: MarsPhotoRetrofitService
    private lateinit var apiKey: String

    private val retrofitCallback = object : Callback<MarsResponseEntity> {
        override fun onResponse(
            call: Call<MarsResponseEntity>,
            response: Response<MarsResponseEntity>
        ) {
            val body: MarsPhotoEntity =
                response.body()?.photos?.get(0) ?: MarsPhotoEntity(EMPTY_MESSAGE)
            loadStateLiveDataMutable.postValue(
                if (response.isSuccessful) {
                    MarsPictureLoadState.Success(body.sol, body.date, body.image)
                } else {
                    MarsPictureLoadState.Error(LoadError.SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<MarsResponseEntity>, t: Throwable) {
            loadStateLiveDataMutable.postValue(MarsPictureLoadState.Error(LoadError.LOAD_ERROR))
        }
    }

    fun initViewModel(repo: MarsPhotoRepo, service: MarsPhotoRetrofitService, apiKey: String) {
        this.repo = repo
        this.service = service
        this.apiKey = apiKey
    }

    fun onViewCreated() = loadEarthPhoto()
    fun reloadClicked() = loadEarthPhoto()

    private fun loadEarthPhoto() {
        loadStateLiveDataMutable.value = MarsPictureLoadState.Loading

        val date = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
        formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        repo.getMarsPhoto(retrofitCallback, service, formattedDate, apiKey)
    }
}