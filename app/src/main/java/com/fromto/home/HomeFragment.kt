package com.fromto.home
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.fromto.R
import com.fromto.databinding.FragmentHomeBinding
import com.fromto.read.ReadmailActivity
import com.fromto.retrofit.HomeResponse
import com.fromto.write.WritePActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),HomeView {
    lateinit var binding: FragmentHomeBinding
    val service = HomeService(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        service.tryCheckedLetter()
        initClikckListener()

        return binding.root
    }

    private fun initClikckListener() {
        //fragment>activity
        binding.homeWriteletterIb.setOnClickListener{
            val intent = Intent (getActivity(), WritePActivity::class.java)
            startActivity(intent)
        }

        binding.homeReadmailIb.setOnClickListener{
            openmail()
        }
    }

    private fun openmail(){
        val fadein = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeout = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        binding.mailopening.visibility = View.VISIBLE
        mailopening.startAnimation(fadein)

        Handler(Looper.getMainLooper()).postDelayed({
//            mailopening.startAnimation(fadeout)
            mailopening.startAnimation(fadein)
            binding.mailopening.setImageResource(R.drawable.letter_opened) },
            1000)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(getActivity(), ReadmailActivity::class.java)
            startActivity(intent)
            binding.mailopening.visibility = View.GONE
        },2500)
    }

    override fun onLoading() {
//        binding.homeLoadingPb.visibility = View.VISIBLE
    }
    override fun onCheckedLetterSuccess(response: HomeResponse) {
        when(response.code){
            1000 -> { //편지 있음
                binding.homeReadmailIb.visibility = View.VISIBLE
                binding.homeBackground.setImageResource(R.drawable.home_background_on)
                when(response.result?.안읽은편지의개수){
                    1 -> binding.homeReadmailIb.setImageResource(R.drawable.home_letter_01)
                    2 -> binding.homeReadmailIb.setImageResource(R.drawable.home_letter_02)
                    3 -> binding.homeReadmailIb.setImageResource(R.drawable.home_letter_03)
                    else -> binding.homeReadmailIb.setImageResource(R.drawable.home_letter_04) }
            }
            1001 -> { //편지 없음
                binding.homeBackground.setImageResource(R.drawable.home_background_off)
            }
        }
    }

    override fun onCheckedLetterFailure(message: String) {
    }

    override fun onReadingLetterSuccess(response: HomeResponse) {
        TODO("Not yet implemented")
    }

    override fun onReadingLetterFailure(message: String) {
        TODO("Not yet implemented")
    }

}