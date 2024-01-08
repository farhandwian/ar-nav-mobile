package polinema.ac.id.pinar_app_astar.presentation.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import polinema.ac.id.pinar_app_astar.data.model.Building
import polinema.ac.id.pinar_app_astar.domain.repository.BuildingRepository
import polinema.ac.id.pinar_app_astar.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(
    private val repository: BuildingRepository
): ViewModel() {
    private var _locations = MutableLiveData<List<Building>>()
    val locations: LiveData<List<Building>> get() = _locations

    fun getBuildings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBuildings().collect { result ->
                if (result is Response.Success) {
                    val data = result.data
                    _locations.postValue(data)
                }
            }
        }
    }

    fun getBuilding(buildingId: String): Building? {
        val buildingList = _locations.value
        return buildingList?.find { it.id == buildingId }
    }
}