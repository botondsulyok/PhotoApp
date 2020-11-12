package hu.bme.photoapp.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import hu.bme.photoapp.home.HomeFragment
import hu.bme.photoapp.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoryFragment : Fragment(), CategoryRecyclerViewAdapter.CategoryItemClickListener {

    private lateinit var recyclerViewAdapter: CategoryRecyclerViewAdapter

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Categories"

        setupRecyclerView()

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            recyclerViewAdapter.addAll(categories)
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = CategoryRecyclerViewAdapter()
        recyclerViewAdapter.itemClickListener = this
        rvCategoryList.adapter = recyclerViewAdapter
    }

    override fun onItemClick(category: Category) {
        val action =
            CategoryFragmentDirections.actionCategorySelected(category._id, HomeFragment.CATEGORY_IMAGES, category.name)
        findNavController().navigate(action)
    }

}