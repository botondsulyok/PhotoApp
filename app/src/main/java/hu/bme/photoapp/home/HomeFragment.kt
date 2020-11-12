package hu.bme.photoapp.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeRecyclerViewAdapter.ImageItemClickListener {

    companion object {
        const val CATEGORY_IMAGES = "CATEGORY_IMAGES"
        const val COMPETITION_IMAGES = "COMPETITION_IMAGES"
    }

    private lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.window?.setBackgroundDrawableResource(R.color.colorBackground)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener{
            val action =
                HomeFragmentDirections.actionPlusButtonClicked()
            findNavController().navigate(action)
        }
        activity?.toolbar_main?.visibility = View.VISIBLE
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        (activity as AppCompatActivity?)?.supportActionBar?.title = arguments?.getString("title")

        setupRecyclerView()

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.allImages.observe(viewLifecycleOwner) { images ->
            recyclerViewAdapter.addAll(images)
        }

        when(arguments?.getString("type")) {
            CATEGORY_IMAGES -> {
                homeViewModel.getImagesByCategory(arguments?.getString("id") ?: "")
            }
            COMPETITION_IMAGES -> {
                homeViewModel.getImagesByCompetition(arguments?.getString("id") ?: "")
            }
            else -> {
                homeViewModel.getAllImages()
            }
        }

}

    private fun setupRecyclerView() {
        recyclerViewAdapter = HomeRecyclerViewAdapter(requireContext())
        recyclerViewAdapter.itemClickListener = this
        rvImageList.adapter = recyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_toolbar, menu)
    }

    //TODO
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(image: Image, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(imageView to image.ownImage)
        val action = HomeFragmentDirections.actionViewPhoto(imageUrl = image.ownImage, id = image._id)
        findNavController().navigate(action, extras)
    }

}