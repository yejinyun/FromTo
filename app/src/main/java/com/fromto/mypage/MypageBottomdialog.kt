package com.fromto.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fromto.R
import com.fromto.databinding.DialogMypageprofileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody.Part as MultipartBodyPart

interface BottomSheetClickListener {
    fun onButtomSheetClicked(type: String)
}

class mypageBottomdialog : BottomSheetDialogFragment() {
    lateinit var binding : DialogMypageprofileBinding
    val service = MypageService(MypageFragment())
    private val OPEN_GALLERY = 1
    var body : MultipartBody.Part? = null

    lateinit var bottomSheetClickListener: BottomSheetClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogMypageprofileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
          bottomSheetClickListener = context as BottomSheetClickListener
        } catch(e: Exception){
           Log.e("BottomDialog","onAttatch Error")
         }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClikckListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initClikckListener() {
        binding.mypageprofileSelect.setOnClickListener {
            openGallery()
        }

        binding.mypageprofileOk.setOnClickListener {
            service.tryChangeProfileImgUrl(body!!)
        }

    }

    private fun openGallery(){
        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == OPEN_GALLERY){
                var currentImageUrl : Uri? = data?.data
                Log.d("Img Uri",currentImageUrl.toString())

//                 Create file path inside app's data dir
                val filePath = (context?.applicationInfo?.dataDir + File.separator
                        + System.currentTimeMillis())
//                val filePath = data?.data?.path
                Log.d("Img path",filePath.toString())

                val file = File(filePath)

                val requestFile = RequestBody.create(MediaType.parse("image"),file)
                body = MultipartBodyPart.createFormData("file",file.name,requestFile)

                try {
                    val contentResolver = context?.contentResolver
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,currentImageUrl)
                    binding.dialogProfileIv.setImageBitmap(bitmap)
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }else{
            Log.d("Activity Result","something wrong")
        }
    }

//    private fun pickImageFromGallery() {
//        //Intent to pick image
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }
//
//    companion object {
//        //image pick code
//        private val IMAGE_PICK_CODE = 1000;
//        //Permission code
//        private val PERMISSION_CODE = 1001;
//    }
//
//    //handle requested permission result
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size >0 && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED){
//                    //permission from popup granted
//                    pickImageFromGallery()
//                }
//                else{
//                    //permission from popup denied
//                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    //handle result of picked image
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        val mActivity = activity as MainActivity
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            binding.dialogProfileIv.setImageURI(data?.data)
//        }
//    }
}
