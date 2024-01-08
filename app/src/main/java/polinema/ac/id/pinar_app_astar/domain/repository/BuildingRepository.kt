package polinema.ac.id.pinar_app_astar.domain.repository

import polinema.ac.id.pinar_app_astar.data.model.Building
import polinema.ac.id.pinar_app_astar.utils.Response
import kotlinx.coroutines.flow.Flow

interface BuildingRepository {
    fun getBuildings(): Flow<Response<List<Building>>>

    suspend fun addBuilding(building: Building): Flow<Response<Void?>>
}