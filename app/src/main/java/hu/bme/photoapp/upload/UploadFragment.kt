package hu.bme.photoapp.upload

import android.Manifest
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
import hu.bme.photoapp.MainActivity
import hu.bme.photoapp.R
import hu.bme.photoapp.authentication.WelcomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.fragment_welcome.*


class UploadFragment : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        make_photo_button.setOnClickListener {
            val permissionResult = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
            when (permissionResult) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermission()
            }
        }
    }

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(requireActivity(),
                    "I need it for camera", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireActivity(), "CAMERA permission granted",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "CAMERA permission NOT granted",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}