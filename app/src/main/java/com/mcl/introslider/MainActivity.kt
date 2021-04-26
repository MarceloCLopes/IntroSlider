package com.mcl.introslider

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val introSlideAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "Sunlight",
                "Sunlight is the light and energy that comes the Sun.",
                R.drawable.sunlight
            ),
            IntroSlide(
                "Pay Online",
                "Electronic bill payment is a feature of online, mobile and telephone banking",
                R.drawable.pay_online
            ),
            IntroSlide(
                "Video Streaming",
                "Streaming media is multimedia that is constantly received by and presented to an end-user",
                R.drawable.video_streaming
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        introSliderViewPager.adapter = introSlideAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        buttonNext.setOnClickListener {
            if(introSliderViewPager.currentItem + 1 < introSlideAdapter.itemCount){
                introSliderViewPager.currentItem += 1
            } else {
                Intent(applicationContext, AnotherActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
        textSkipIntro.setOnClickListener {
            Intent(applicationContext, AnotherActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index){
                imageView.setImageDrawable(
                     ContextCompat.getDrawable(
                         applicationContext,
                         R.drawable.indicator_active
                     )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}