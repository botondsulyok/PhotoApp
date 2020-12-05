package hu.bme.photoapp.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import hu.bme.photoapp.R
import hu.bme.photoapp.home.HomeFragment
import hu.bme.photoapp.home.HomeViewModel
import hu.bme.photoapp.home.Image
import kotlinx.android.synthetic.main.fragment_upload.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class UploadFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val args: UploadFragmentArgs by navArgs()

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1000
        private const val IMAGE_CAPTURE_PERMISSION_CODE = 1001
        private const val  REQUEST_IMAGE_PICK = 1002
        private const val IMAGE_PICK_PERMISSION_CODE = 1003
        private var IMAGE_URI: Uri? = null
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

        make_photo_button.setOnClickListener {
            requestNeededPermissionCamera()
        }

        upload_photo.setOnClickListener {
            requestNeededPermissionReadStorage()
        }

        upload_button.setOnClickListener {
            var valid = true
            if(ivPhoto.visibility == View.GONE) {
                Toast.makeText(activity, getString(R.string.txt_attachphoto), Toast.LENGTH_LONG).show()
                valid = false
            }
            if (name_text_field.text.isEmpty()) {
                name_text_field.error = getString(R.string.error_empty)
                valid = false
            }
            if (description_field.text.isEmpty()) {
                description_field.error = getString(R.string.error_empty)
                valid = false
            }

            if (valid) {
                homeViewModel.postPhoto(
                    filePath = currentPhotoPath,
                    title = name_text_field.text.toString(),
                    description = description_field.text.toString(),
                    onSuccess = this::uploadSuccess,
                    onError = this::uploadError
                )
            }
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File = createImageFile()
        IMAGE_URI = FileProvider.getUriForFile(
            requireActivity(),
            "hu.bme.aut.android.photoappfileprovider",
            photoFile
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, IMAGE_URI)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    lateinit var currentPhotoPath: String
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun uploadSuccess(image: Image) {
        //feltöltötte a fotót, ezután kategóriához kell rendelni
        if(args.id == "") {
            uploadDone()
        }
        else {
            when(args.type) {
                HomeFragment.CATEGORY_IMAGES -> {
                    homeViewModel.addImageToCategory(args.id, image._id, this::uploadDone, this::uploadError)
                }
                HomeFragment.COMPETITION_IMAGES -> {
                    homeViewModel.addImageToCompetition(args.id, image._id, this::uploadDone, this::uploadError)
                }
            }
        }

    }

    private fun uploadDone() {
        Toast.makeText(activity, getString(R.string.txt_successfullyuploaded), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun uploadError(e: Throwable) {
        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(this).load(IMAGE_URI).into(ivPhoto)
            ivPhoto.visibility = View.VISIBLE
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PICK) {
            IMAGE_URI = data?.data
            Glide.with(this).load(IMAGE_URI).into(ivPhoto)
            ivPhoto.visibility = View.VISIBLE
            val bitmap = data?.data?.let { getBitmapFromUri(it) }
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            currentPhotoPath=createImageFile().absolutePath
            saveImageOnExternalData(currentPhotoPath,image)
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            requireContext().contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }


    private fun requestNeededPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA)
            ) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.txt_needitforcamera), Toast.LENGTH_SHORT
                ).show()
            }
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                IMAGE_CAPTURE_PERMISSION_CODE
            )
        }
        else
            startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            IMAGE_CAPTURE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireActivity(), getString(R.string.txt_permissiongranted),
                        Toast.LENGTH_SHORT
                    ).show()
                    startCamera()
                } else {
                    Toast.makeText(
                        requireActivity(), getString(R.string.txt_permissionnotgranted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            IMAGE_PICK_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGallery()
                    }
            }
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
                    getString(R.string.txt_needitforupload), Toast.LENGTH_SHORT
                ).show()
            }
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                IMAGE_PICK_PERMISSION_CODE
            )
        }
        else
            pickImageFromGallery()
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
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return isFileSaved
    }
}