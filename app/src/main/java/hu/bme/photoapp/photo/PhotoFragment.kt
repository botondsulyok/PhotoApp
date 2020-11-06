package hu.bme.photoapp.photo

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import hu.bme.photoapp.R
import hu.bme.photoapp.home.ImageAPI
import kotlinx.android.synthetic.main.fragment_photo.*


class PhotoFragment : Fragment() {

    val args: PhotoFragmentArgs by navArgs()

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
        ivPhoto.apply {
            transitionName = args.imageUrl
            val imageUrl = transitionName.replace("\\", "/")
            Glide.with(this).load(ImageAPI.BASE_URL+imageUrl).into(ivPhoto)
        }

    }


}