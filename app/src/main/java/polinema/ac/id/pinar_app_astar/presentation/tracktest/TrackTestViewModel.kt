package polinema.ac.id.pinar_app_astar.presentation.tracktest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import polinema.ac.id.pinar_app_astar.data.model.research.FailReport
import polinema.ac.id.pinar_app_astar.domain.repository.ReportRepository
import polinema.ac.id.pinar_app_astar.utils.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TrackTestViewModel(
    private val repository: ReportRepository
) : ViewModel() {

    private val _status = MutableLiveData<Event<Boolean>>()
    val status get() = _status

    fun newReport(report: FailReport) {
        viewModelScope.launch {
            repository.newReport(report)
                .addOnSuccessListener { _status.postValue(Event(true)) }
                .addOnFailureListener { _status.postValue(Event(false)) }
                .await()
        }
    }
}