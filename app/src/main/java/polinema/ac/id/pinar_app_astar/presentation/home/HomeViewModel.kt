package polinema.ac.id.pinar_app_astar.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import polinema.ac.id.pinar_app_astar.data.model.Building
import polinema.ac.id.pinar_app_astar.domain.repository.BuildingRepository
import polinema.ac.id.pinar_app_astar.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BuildingRepository
) : ViewModel() {
    private val _buildingState = MutableLiveData<Response<List<Building>>>()
    val buildingState: LiveData<Response<List<Building>>> get() = _buildingState

    init {
        getBuildings()
    }

    fun getBuildings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBuildings().collect {
                _buildingState.postValue(it)
            }
        }
    }
}