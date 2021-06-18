package com.kartikaymahajan.travellerbackpack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.kartikaymahajan.travellerbackpack.databinding.FragmentScanTextBinding
import java.io.File
import java.io.IOException


@Suppress("DEPRECATION")
class ScanTextFragment : Fragment() {
    private var _binding: FragmentScanTextBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private var scannedText: String = ""

    lateinit var savedUri: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanTextBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.galleryChoseBtn.setOnClickListener {
            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(
                intent, "Choose Image"), 122)
        }

        binding.cameraChoseBtn.setOnClickListener {
            var intent = Intent(requireContext(),CameraActivity::class.java)
            startActivityForResult(intent ,123)
        }
        return view
    }

    private fun scanTextFromImage(uri:Uri){
        binding.imageView.setImageURI(uri)
        val image: InputImage
        try {
            image = InputImage.fromFilePath(requireActivity(), uri)
            val recognizer = TextRecognition.getClient()
            recognizer.process(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    // ...

                    binding.editTxtView.setText(it.text)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data == null){
            Toast.makeText(requireContext(),"No Image Selected",Toast.LENGTH_SHORT).show()
            return
        }

        if (requestCode == 122) {
            scanTextFromImage(data.data!!)
        }

        if (requestCode==123){
            savedUri = data.getStringExtra("savedUri").toString()

            val photoUri = Uri.parse(savedUri)!!

            Log.e("Scan Fragment","Saved Uri Path= ${photoUri.path}")

            val uri = Uri.fromFile(File(photoUri.path!!))
            scanTextFromImage(uri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
