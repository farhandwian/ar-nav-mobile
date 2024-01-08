package polinema.ac.id.pinar_app_astar.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import polinema.ac.id.pinar_app_astar.R
import polinema.ac.id.pinar_app_astar.data.model.Building
import polinema.ac.id.pinar_app_astar.data.model.POI
import polinema.ac.id.pinar_app_astar.databinding.FragmentDetailBinding
import polinema.ac.id.pinar_app_astar.presentation.adapter.detail.FloorPagerAdapter
import polinema.ac.id.pinar_app_astar.presentation.sharedviewmodel.NavigationViewModel
import polinema.ac.id.pinar_app_astar.utils.Response
import polinema.ac.id.pinar_app_astar.utils.UIHelper.popBackFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val viewModel by sharedViewModel<NavigationViewModel>()

    private val args: DetailFragmentArgs by navArgs()
    private val building: Building by lazy { args.building }

    private lateinit var data: List<POI>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNodes(building.id)
        viewModel.sharedCurrentBuilding = building.id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBuildingDetail()
        setupFloorPage()
    }

    private fun setupBuildingDetail() {
        binding.topAppBar.apply {
            title = building.id
            setNavigationOnClickListener { popBackFragment(it) }
            setOnMenuItemClickListener { menuItemClick(it) }
        }
    }

    private fun menuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                findNavController().navigate(
                    DetailFragmentDirections.toEditBuilding(building)
                )
                true
            }
            R.id.action_add_node -> {
                findNavController().navigate(
                    DetailFragmentDirections.toScanFragment(null)
                )
                true
            }
            R.id.action_test -> {
                findNavController().navigate(
                    DetailFragmentDirections.toTestFragment(building.id)
                )
                true
            }
            else -> false
        }
    }

    private fun setupFloorPage() {
        viewModel.nodeList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    data = response.data

                    viewModel.sharedNodeList.clear()
                    viewModel.sharedNodeList.addAll(data)

                    if (viewModel.filterNodes().isEmpty()) {
                        toggleTabLayout(false)
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.pager.adapter = FloorPagerAdapter(this, building.floors)
                        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                            tab.text = setTabText(position)
                        }.attach()
                        toggleTabLayout(true)
                    }
                }
                is Response.Error -> {
                    toggleTabLayout(false)
                    binding.emptyView.visibility = View.VISIBLE
                }
                Response.Loading -> {}
            }
        }
    }

    private fun toggleTabLayout(needed: Boolean){
        binding.roomView.visibility = if (needed) View.VISIBLE else View.GONE
    }

    private fun setTabText(position: Int): CharSequence =
        if (building.floors > 3) {
            (position + 1).toString()
        } else {
            getString(R.string.lbl_item_floor, (position + 1))
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}