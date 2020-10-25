package hu.bme.photoapp.upload

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import hu.bme.photoapp.authentication.WelcomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.fragment_welcome.*


class UploadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        make_photo_button.setOnClickListener {

        }
    }

}