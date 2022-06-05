package com.example.amunstore.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import com.example.amunstore.databinding.FragmentCategoriesBinding
import com.example.amunstore.ui.categories.singlecategory.SingleCategoryFragment
import com.example.amunstore.ui.vendorproduct.ProductVendorFragmentDirections
import com.example.example.CustomCollections
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val viewModel: CategoriesViewModel by viewModels()
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val titleList = arrayListOf<CustomCollections>()
        val fragmentList = arrayListOf<SingleCategoryFragment>()

        viewModel.categoriesList.observe(viewLifecycleOwner) {
            titleList.clear()
            fragmentList.clear()
            if (it != null) {
                for (category in it) {
                    titleList.add(category)
                    fragmentList.add(SingleCategoryFragment(category) {
                        val action =
                            it.id?.let { it1 ->
                                CategoriesFragmentDirections.actionNavigationCategoriesToProductDetailsFragment(
                                    it1)
                            }
                        view?.let { it1 ->
                            action?.let { it2 ->
                                Navigation.findNavController(it1).navigate(it2)
                            }
                        }
                    })
                }
                binding.categoriesViewPager.adapter = ViewPagerCategoriesAdapter(
                    fragmentList, requireActivity()
                )

                TabLayoutMediator(
                    binding.categoriesTabLayout,
                    binding.categoriesViewPager
                ) { tab, position ->
                    tab.text = titleList[position].title
                }.attach()
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.getCategories()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
