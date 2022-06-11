package com.jerry1012.memorygame

import android.content.Intent
import android.graphics.Color.alpha
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val imgviewsSrc = listOf<Int>(R.drawable.ic_0,R.drawable.ic_1,R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_4,R.drawable.ic_5,R.drawable.ic_6,R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9)
        val ivImageview1 = findViewById<ImageView>(R.id.iv_imageView1)
        val ivImageview2 = findViewById<ImageView>(R.id.iv_imageView2)
        val ivImageview3 = findViewById<ImageView>(R.id.iv_imageView3)
        val imgArray = listOf<ImageView>(ivImageview1,ivImageview2,ivImageview3)
        val tvappname = findViewById<TextView>(R.id.tv_appname)
        for (i in imgArray){
            var temp = imgviewsSrc.random()
            i.setImageResource(temp)
        }

        tvappname.animate().apply {
            duration = 4000
            alpha(1f)
            ivImageview1.animate().apply {
                duration = 4000
                alpha(1f)
                ivImageview2.animate().apply {
                    duration = 4000
                    alpha(1f)
                    ivImageview3.animate().apply {
                        duration = 4000
                        alpha(1f)

                    }
                }
            }
        }.withEndAction {
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }.start()


       



    }
}