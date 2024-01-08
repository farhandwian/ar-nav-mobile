package polinema.ac.id.pinar_app_astar.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import polinema.ac.id.pinar_app_astar.data.model.research.FailReport

interface ReportRepository {
    suspend fun newReport(report: FailReport): Task<DocumentReference>
}