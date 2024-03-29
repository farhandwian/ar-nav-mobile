package polinema.ac.id.pinar_app_astar.presentation.scan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import polinema.ac.id.pinar_app_astar.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScanViewModel: ViewModel() {
    private val _qrValue = MutableLiveData<Event<Int?>>()
    val qrValue get() = _qrValue

    fun onQrRecognized(code: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _qrValue.postValue(Event(code))
        }
    }
}