package polinema.ac.id.pinar_app_astar.utils

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<T>(val data: T): Response<T>()
    data class Error(val message: String): Response<Nothing>()
}
