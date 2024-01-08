package polinema.ac.id.pinar_app_astar.presentation.home.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import polinema.ac.id.pinar_app_astar.data.model.Building
import polinema.ac.id.pinar_app_astar.domain.repository.BuildingRepository
import polinema.ac.id.pinar_app_astar.utils.Event
import polinema.ac.id.pinar_app_astar.utils.Response
import polinema.ac.id.pinar_app_astar.utils.StorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeFormViewModel(
    private val repository: BuildingRepository
): ViewModel() {
    private var _thumbnailList = MutableLiveData<List<String>>()
    val thumbnailList get() = _thumbnailList

    private var _message = MutableLiveData<Event<Boolean>>()
    val message get() = _message

    fun addBuilding(building: Building) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBuilding(building).collect { response ->
                when (response) {
                    is Response.Success -> _message.postValue(Event(true))
                    is Response.Error -> _message.postValue(Event(false))
                    Response.Loading -> {}
                }
            }
        }
    }

    /*
        Function to upload images to StorageUtil and get the url of the images
     */
    fun getThumbnail() {
        val thumbnails = mutableListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            StorageHelper.getListThumbnail()
                .addOnSuccessListener { result ->
                    result.items.forEach { item ->
                        thumbnails.add(item.name)
                    }
                    _thumbnailList.postValue(thumbnails)
                }
                .addOnFailureListener {
                    println("Error $it")
                }
                .await()
        }
    }
}