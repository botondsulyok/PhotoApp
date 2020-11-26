package hu.bme.photoapp.upload

import android.Manifest
import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
<<<<<<< HEAD
<<<<<<< HEAD
import com.bumptech.glide.Glide
import com.koushikdutta.ion.Ion
=======
>>>>>>> parent of c959613... a
=======
>>>>>>> parent of c959613... a
import hu.bme.photoapp.R
import hu.bme.photoapp.home.HomeViewModel
import hu.bme.photoapp.model.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_upload.*
import okhttp3.ResponseBody
import java.io.*


class UploadFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    companion object {
        private const val PERMISSION_REQUEST_CODE = 1000
        private const val REQUEST_IMAGE_CAPTURE = 1001
        private const val IMAGE_PICK_CODE = 1002
        private var IMAGE_PATH = "/storage/emulated/0/DCIM/Camera/tmp_image.jpg"
        private var IMAGE_URI = Uri.fromFile(File(IMAGE_PATH))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Upload Photo"

        make_photo_button.setOnClickListener {
            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            )) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermissionCamera()
            }

            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermissionWriteStorage()
            }

            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermissionReadStorage()
            }


            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        upload_photo.setOnClickListener {
            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermissionReadStorage()
            }
            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )) {
                PackageManager.PERMISSION_DENIED -> requestNeededPermissionWriteStorage()
            }
            pickImageFromGallery()
        }

        upload_button.setOnClickListener {
<<<<<<< HEAD
<<<<<<< HEAD
            context?.let { it1 ->
                homeViewModel.postPhoto(
                    filePath = currentPhotoPath,
                    title = name_text_field.text.toString(),
                    description = description_field.text.toString(),
                    context = it1,
                    onSuccess = this::uploadSuccess,
                    onError = this::uploadError
                )
            }
        }
    }

    lateinit var currentPhotoPath: String
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun uploadSuccess(responseBody: ResponseBody?) {
=======
            homeViewModel.postPhoto(
                fileUri = IMAGE_URI,
                title = name_text_field.text.toString(),
                description = description_field.text.toString(),
                onSuccess = this::uploadSuccess,
                onError = this::uploadError
            )
        }
    }

=======
            homeViewModel.postPhoto(
                fileUri = IMAGE_URI,
                title = name_text_field.text.toString(),
                description = description_field.text.toString(),
                onSuccess = this::uploadSuccess,
                onError = this::uploadError
            )
        }
    }

>>>>>>> parent of c959613... a
    private fun uploadSuccess(responseBody: ResponseBody) {
>>>>>>> parent of c959613... a
        Toast.makeText(activity, "Successfully uploaded!", Toast.LENGTH_SHORT).show()
        val action =
            UploadFragmentDirections.actionUploadFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun uploadError(e: Throwable) {
        Toast.makeText(activity, "Error during uploading photo!", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ivPhoto.setImageBitmap(imageBitmap)
            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            saveImageOnExternalData(IMAGE_PATH, byteArray)
            IMAGE_URI = Uri.fromFile(File(IMAGE_PATH))
        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            ivPhoto.setImageURI(data?.data)
            //TODO nem jó a file uri a név miatt
            Log.e("uri", data?.data?.lastPathSegment.toString())
        }
    }


    private fun requestNeededPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(
                    requireActivity(),
                    "I need it for camera", Toast.LENGTH_SHORT
                ).show()
            }
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireActivity(), "permission granted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(), "permission NOT granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun requestNeededPermissionWriteStorage() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    requireActivity(),
                    "I need it for camera", Toast.LENGTH_SHORT
                ).show()
            }
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun requestNeededPermissionReadStorage() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    requireActivity(),
                    "I need it for camera", Toast.LENGTH_SHORT
                ).show()
            }
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun saveImageOnExternalData(filePath: String?, fileData: ByteArray?): Boolean {
        var isFileSaved = false
        try {
            val f = File(filePath.toString())
            if (f.exists()) f.delete()
            f.createNewFile()
            val fos = FileOutputStream(f)
            fos.write(fileData)
            fos.flush()
            fos.close()
            isFileSaved = true
        } catch (e: FileNotFoundException) {
            println("FileNotFoundException")
            e.printStackTrace()
        } catch (e: IOException) {
            println("IOException")
            e.printStackTrace()
        }
        return isFileSaved
    }
}