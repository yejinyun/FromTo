package com.fromto.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fromto.R
import com.fromto.write.model.ContentDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_selectmovie.*
import java.text.SimpleDateFormat
import java.util.*

class AddphotoActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage: FirebaseStorage? = null
    //img uri 담기
    var photoUri: Uri? = null
    var auth : FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectmovie)

        // Firebase storage 초기화
        storage = FirebaseStorage.getInstance()
        // Firebase Database
        firestore = FirebaseFirestore.getInstance()
        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        addphoto_img.setOnClickListener {
            //Open the album
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }

        addphto_upload_btn.setOnClickListener {
            contentUpload()
        }
    }

    //선택한 이미지를 받는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            //이미지 선택시
            if(resultCode == Activity.RESULT_OK){
                //이미지뷰에 이미지 세팅, 사진 경로를 받아옴
                println(data?.data)
                photoUri = data?.data
                addphoto_img.setImageURI(data?.data)
            }

            else{
                //취소버튼 누른 경우
                finish()
            }

        }
    }

    fun contentUpload(){
        //make file name
//        progressBar.visibility = View.VISIBLE

        //이름이 중복 생성되지 않도록 날짜로 파일명 만들기
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)

        //Promise method
        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            //시간 생성
            var contentDTO = ContentDTO()
            //게시물을 데이터를 생성 및 엑티비티 종료
            firestore?.collection("images")?.document()?.set(contentDTO)

            //이미지 주소
            contentDTO.imageUrl = uri!!.toString()
            //유저의 UID
            contentDTO.uid = auth?.currentUser?.uid
            //게시물의 설명
            contentDTO.title = addphto_edit_title.text.toString()
            contentDTO.explain = addphto_edit_explain.text.toString()
            //유저의 아이디
            contentDTO.userId = auth?.currentUser?.email
            //게시물 업로드 시간
            contentDTO.timestamp = System.currentTimeMillis()

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)

            finish()
        }
//        //upload (callback method) 데이터 베이스 입력 code
//        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
//            //이미지 주소 받아오기
//            storageRef.downloadUrl.addOnSuccessListener { uri ->
//                //시간 생성
//                var contentDTO = ContentDTO()
//                //게시물을 데이터를 생성 및 엑티비티 종료
//                firestore?.collection("images")?.document()?.set(contentDTO)
//
//                //이미지 주소
//                contentDTO.imageUrl = uri!!.toString()
//                //유저의 UID
//                contentDTO.uid = auth?.currentUser?.uid
//                //게시물의 설명
//                contentDTO.explain = addphto_edit_explain.text.toString()
//                //유저의 아이디
//                contentDTO.userId = auth?.currentUser?.email
//                //게시물 업로드 시간
//                contentDTO.timestamp = System.currentTimeMillis()
//
//                firestore?.collection("images")?.document()?.set(contentDTO)
//
//                setResult(Activity.RESULT_OK)
//
//                finish()
//            }
//
            Toast.makeText(
                this, "upload Success!",
                Toast.LENGTH_SHORT
            ).show()

//            val uri = taskSnapshot.downloadUrl
//            //디비에 바인딩 할 위치 생성 및 컬렉션(테이블)에 데이터 집합 생성
//
//
//
//            setResult(Activity.RESULT_OK)
//            finish()
//        }
//            ?.addOnFailureListener {
//                progressBar.visibility = View.GONE
//
//                Toast.makeText(this, getString(R.string.upload_fail),
//                    Toast.LENGTH_SHORT).show()
//            }
//    }

        }
    }