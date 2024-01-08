package polinema.ac.id.pinar_app_astar.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import polinema.ac.id.pinar_app_astar.data.model.research.FailReport
import polinema.ac.id.pinar_app_astar.domain.repository.ReportRepository
import polinema.ac.id.pinar_app_astar.utils.REPORT_PATH

class ReportRepositoryImpl: ReportRepository {
    private val db = Firebase.firestore
    private val rootCollection = db.collection(REPORT_PATH)

    override suspend fun newReport(report: FailReport): Task<DocumentReference> =
        rootCollection.add(report)
}