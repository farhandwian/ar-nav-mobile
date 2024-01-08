package polinema.ac.id.pinar_app_astar.presentation.maps.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import polinema.ac.id.pinar_app_astar.R
import polinema.ac.id.pinar_app_astar.databinding.FragmentMapsDialogBinding
import polinema.ac.id.pinar_app_astar.presentation.maps.MapsViewModel
import polinema.ac.id.pinar_app_astar.utils.StorageHelper.getThumbnail
import polinema.ac.id.pinar_app_astar.utils.UIHelper.showToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MapsDialogFragment : DialogFragment() {
    private var _binding: FragmentMapsDialogBinding? = null
    private val binding get() = _binding as FragmentMapsDialogBinding

    private val viewModel by sharedViewModel<MapsViewModel>()

    private var buildingId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("ID")?.let {
            buildingId = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBody()
    }

    private fun setupBody() {
        val building = viewModel.getBuilding(buildingId)

        if (building != null) {
            val name = if (building.type != "academy") building.name else building.id
            val location = "${building.location.latitude},${building.location.longitude}"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$location($name)?z=16")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            getThumbnail(building.thumbnail)
                .addOnSuccessListener { loadThumbnail(it) }
                .addOnFailureListener {
                    loadThumbnail(null)
                    showToast(requireContext(), "Cannot Load Thumbnail Image")
                }
            binding.apply {
                tvId.text = name
                tvDescription.text = building.description
            }

            binding.btnMap.setOnClickListener {
                startActivity(mapIntent)
            }
        }
    }

    private fun loadThumbnail(uri: Uri?) {
        if (uri != null) {
            binding.ivThumbnail.load(uri) { placeholder(R.drawable.ic_image) }
        } else {
            binding.ivThumbnail.load(R.drawable.ic_image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String) = MapsDialogFragment().apply {
            arguments = Bundle().apply {
                putString("ID", title)
            }
        }
    }
}