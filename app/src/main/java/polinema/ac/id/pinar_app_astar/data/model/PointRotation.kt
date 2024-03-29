package polinema.ac.id.pinar_app_astar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointRotation(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
    var w: Float = 0f
): Parcelable
