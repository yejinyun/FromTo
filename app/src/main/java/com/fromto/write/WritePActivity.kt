package com.fromto.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.ActivityWritepersonalBinding
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.WritePContens
import kotlinx.android.synthetic.main.activity_writepersonal.*

class WritePActivity  : AppCompatActivity(),WritePView {
    lateinit var binding: ActivityWritepersonalBinding
    private val OPEN_GALLERY = 1
    val service = WritePService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritepersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.fade_in, R.anim.none)

        importGenderButton()
        importAgeSpinner()
        importSpoCheckBox()
        service.tryWritingLetter()
        initClikckListener()

    }
    private fun initClikckListener() {

        //activity > activity
        binding.writePCloseIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) }

        binding.writePPosterIv.setOnClickListener {
            openGallery()
        }

        binding.writePSendIv.setOnClickListener {
            val letterTitle: String = binding.writePMailtitleEt.text.toString()
            val movieTitle: String = binding.writePMovietitleEt.text.toString()
            val contents: String = binding.writePContentsEt.text.toString()
            val posterurl: String = prefs.getString("WritePImgUrl","").toString()
            val preferAge: Int = prefs.getInt("Prefer_Age", 0)
            val preferGender: Int = prefs.getInt("Prefer_Gender", 0)
            val spoStatus : Boolean = prefs.getBoolean("spoStatus",false)

            val getWritePContens = WritePContens(letterTitle,movieTitle,contents,posterurl, preferAge, preferGender, spoStatus)
            Log.d("getWritePContens",getWritePContens.toString())

            service.trySendingLetter(getWritePContens)
        }
    }

    private fun importGenderButton(){
        writeP_gender_radiogroup.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.writeP_gender_0 -> { prefs.edit().putInt("Prefer_Gender", 0).apply() }

                R.id.writeP_gender_m -> { prefs.edit().putInt("Prefer_Gender", 1).apply() }

                R.id.writeP_gender_w -> { prefs.edit().putInt("Prefer_Gender", 2).apply() }
                else -> {
                    Toast.makeText(this@WritePActivity, "선호하는 수신자의 성별을 선택해주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun importAgeSpinner(){
        val ageList = resources.getStringArray(R.array.preferAge)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, ageList)
        binding.writePAgeSpinner.adapter = adapter
        binding.writePAgeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@WritePActivity, ageList[position], Toast.LENGTH_SHORT).show()
                when(position){
                0 -> { prefs.edit().putInt("Prefer_Age", 0).apply() }
                1 -> { prefs.edit().putInt("Prefer_Age", 10).apply() }
                2 -> { prefs.edit().putInt("Prefer_Age", 20).apply() }
                3 -> { prefs.edit().putInt("Prefer_Age", 30).apply() }
                4 -> { prefs.edit().putInt("Prefer_Age", 40).apply() }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@WritePActivity, "선호하는 수신자의 나이대를 선택해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun importSpoCheckBox(){
        binding.spoCheckbox.setOnClickListener{
            if(binding.spoCheckbox.isChecked){
                prefs.edit().putBoolean("spoStatus",true).apply()
            } else {
                prefs.edit().putBoolean("spoStatus",false).apply()
            }
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
                Log.d("Activity Result",currentImageUrl.toString())
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,currentImageUrl)
                    prefs.edit().putString("WritePImgUrl", currentImageUrl.toString()).apply()
                    binding.writePPosterIv.setImageBitmap(bitmap)
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }else{
            Log.d("Activity Result","something wrong")
        }
    }

    override fun onLoading() { binding.writepLoadingPb.visibility = View.VISIBLE }

    override fun onWritingLetterSuccess(response: AuthResponse) {
        binding.writePFromTv.append(response.result?.nickname)
    }

    override fun onWritingLetterFailure(message: String) {
    }

    override fun onSendingLetterSuccess(response: AuthResponse) {
        when(response.code){
            1000 -> {
                Toast.makeText(this,"편지를 성공적으로 전송했습니다!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
            2025 -> {
                Toast.makeText(this,"모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show() }
            2026 -> {
                Toast.makeText(this,"posterUrl을 입력해주세요.", Toast.LENGTH_SHORT).show() }
            2027 -> {
                Toast.makeText(this,"필터에 맞는 수신자가 DB에 존재하지 않습니다.", Toast.LENGTH_SHORT).show() }
            2000 -> {
                Toast.makeText(this,"JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show() }
            3000 -> {
                Toast.makeText(this,"JWT 토클 검증 실패", Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onSendingLetterFailure(message: String) {
    }
}
