package hu.bme.photoapp.photo

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.photoapp.R
import hu.bme.photoapp.home.HomeFragmentDirections
import hu.bme.photoapp.home.HomeViewModel
import hu.bme.photoapp.home.Image
import hu.bme.photoapp.home.ImageAPI
import kotlinx.android.synthetic.main.fragment_photo.*


class PhotoFragment : Fragment() {

    private lateinit var recyclerViewAdapter: CommentRecyclerViewAdapter

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var commentViewModel: CommentViewModel

    private var image = Image()

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

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Photo"
        (activity as AppCompatActivity?)?.supportActionBar?.show()

        val imageUrl: String
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        ivPhoto.apply {
            transitionName = args.imageUrl
            imageUrl = transitionName.replace("\\", "/")
            Glide.with(this).load(ImageAPI.BASE_URL+imageUrl).into(ivPhoto)
        }
        btnLike.setOnClickListener {
            homeViewModel.likeImage(args.id, (image.likes+1).toString(), this::onSuccessLike)
        }

        homeViewModel.getImage(args.id, this::onSuccessGetImage)

        setupRecyclerView()

        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        commentViewModel.allComments.observe(viewLifecycleOwner) { commentContainer ->
            recyclerViewAdapter.addAll(commentContainer[0].comment.toList())
            tvComments.text = "Comments: " + recyclerViewAdapter.itemCount
        }

        btnComment.setOnClickListener {
            if(etComment.text?.isEmpty() == false) {
                commentViewModel.postComment(image._id, etComment.text.toString(), this::onSuccessComment)
            }
        }

        ivPhoto.setOnClickListener {
            val action =
                PhotoFragmentDirections.actionPhotoClicked(imageUrl)
            findNavController().navigate(action)
        }

    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = CommentRecyclerViewAdapter(requireContext())
        rvCommentList.adapter = recyclerViewAdapter
    }

    private fun onSuccessGetImage(i: Image) {
        if(nestedScrollViewFragmentPhoto != null) {
            image = i
            (activity as AppCompatActivity?)?.supportActionBar?.title = image.title
            tvPhotoName.text = image.title
            tvCreator.text = image.ownerID.email
            tvLikes.text = "Likes: " + image.likes.toString()
            tvDesc.text = image.description

            commentViewModel.getAllComments(image._id)
        }
    }

    private fun onSuccessLike() {
        view?.let { Snackbar.make(it, "Liked", Snackbar.LENGTH_SHORT)
            .setAction("Close") {

            }
            .show() }

        homeViewModel.getImage(args.id, this::onSuccessGetImage)
    }

    private fun onSuccessComment() {
        view?.let { Snackbar.make(it, "Done", Snackbar.LENGTH_SHORT)
            .setAction("Close") {

            }
            .show() }

        etComment.setText("")
        commentViewModel.getAllComments(image._id)
    }


}