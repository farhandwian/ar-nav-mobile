package polinema.ac.id.pinar_app_astar.domain.repository

import polinema.ac.id.pinar_app_astar.data.model.POI
import polinema.ac.id.pinar_app_astar.utils.Response
import kotlinx.coroutines.flow.Flow

interface PathNodeRepository {
    fun getPathNodes(buildingId: String): Flow<Response<List<POI>>>
    suspend fun addPathNode(buildingId: String, poi: POI): Flow<Response<Void?>>
    suspend fun insertNeighbour(buildingId: String, poi: POI, newNeighbour: String): Flow<Response<Void?>>
}