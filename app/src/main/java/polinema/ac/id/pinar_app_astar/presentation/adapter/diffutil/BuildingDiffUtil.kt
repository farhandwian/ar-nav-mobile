package polinema.ac.id.pinar_app_astar.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import polinema.ac.id.pinar_app_astar.data.model.Building

class BuildingDiffUtil(
    private val oldList: List<Building>,
    private val newList: List<Building>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            else -> true
        }
    }
}