package hu.bme.photoapp.photo

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.photoapp.R
import hu.bme.photoapp.home.HomeRecyclerViewAdapter
import hu.bme.photoapp.home.HomeViewModel
import hu.bme.photoapp.home.Image
import hu.bme.photoapp.home.ImageAPI
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_photo.*


class PhotoFragment : Fragment() {

    private lateinit var recyclerViewAdapter: CommentRecyclerViewAdapter

    private lateinit var homeViewModel: HomeViewModel

    private val args: PhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        ivPhoto.apply {
            transitionName = args.imageUrl
            val imageUrl = transitionName.replace("\\", "/")
            Glide.with(this).load(ImageAPI.BASE_URL+imageUrl).into(ivPhoto)
        }
        btnLike.setOnClickListener {
            homeViewModel.likeImage(args.id, this::onSuccessLike)
        }

        homeViewModel.getImage(args.id, this::onSuccessGetImage)

        setupRecyclerView()





        val demo = listOf(
            Comment("user", "text"),
            Comment("user", "text"),
            Comment("user", "text"),
            Comment("user", "text")
        )

        recyclerViewAdapter.addAll(demo)

        /*
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.allImages.observe(viewLifecycleOwner) { images ->
            recyclerViewAdapter.addAll(images)
        }
         */
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = CommentRecyclerViewAdapter(requireContext())
        rvCommentList.adapter = recyclerViewAdapter
    }

    private fun onSuccessGetImage(image: Image) {
        tvPhotoName.text = image.title
        //tvCreator.text = image.owner
        tvLikes.text = image.likes.toString()
        //tvPhotoName.visibility = View.VISIBLE
        //TODO tvDesc.text = image.desc

    }

    private fun onSuccessLike() {
        activity?.window?.decorView?.let { Snackbar.make(it, "Liked", Snackbar.LENGTH_SHORT).show() }
    }


}