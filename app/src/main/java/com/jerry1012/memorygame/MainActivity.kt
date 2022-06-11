package com.jerry1012.memorygame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    val btnBackground = listOf<Int>(R.drawable.ic_0, R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9)
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var runnable = Runnable { }
        var handler =Handler()
        val btnstart = findViewById<Button>(R.id.btn_start)
        val btnrestart = findViewById<Button>(R.id.btn_restart)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        var numMap = mutableMapOf<Int, Int>(0 to 0)
        var score:Int = 0
        var correct : Boolean = false
        numMap = restartInput()
        var clicked:Int = 1
        var currentbutton: ImageView? = null
        var previousbutton: ImageView? = null
        var prevvalue : Int? = null
        var currvalue: Int? = null
        val btn1 = findViewById<ImageView>(R.id.btn_1)
        val btn2 = findViewById<ImageView>(R.id.btn_2)
        val btn3 = findViewById<ImageView>(R.id.btn_3)
        val btn4 = findViewById<ImageView>(R.id.btn_4)
        val btn5 = findViewById<ImageView>(R.id.btn_5)
        val btn6 = findViewById<ImageView>(R.id.btn_6)
        val btn7 = findViewById<ImageView>(R.id.btn_7)
        val btn8 = findViewById<ImageView>(R.id.btn_8)
        val btn9 = findViewById<ImageView>(R.id.btn_9)
        val btn10 = findViewById<ImageView>(R.id.btn_10)
        val btn11 = findViewById<ImageView>(R.id.btn_11)
        val btn12 = findViewById<ImageView>(R.id.btn_12)
        val btn13 = findViewById<ImageView>(R.id.btn_13)
        val btn14 = findViewById<ImageView>(R.id.btn_14)
        val btn15 = findViewById<ImageView>(R.id.btn_15)
        val btn16 = findViewById<ImageView>(R.id.btn_16)
        val btn17 = findViewById<ImageView>(R.id.btn_17)
        val btn18 = findViewById<ImageView>(R.id.btn_18)
        val btn19 = findViewById<ImageView>(R.id.btn_19)
        val btn20 = findViewById<ImageView>(R.id.btn_20)
        val tvTime = findViewById<TextView>(R.id.tv_timeleft)
        var temp1:ImageView? = null
        var temp2:ImageView? =null
        var inputMap = mutableMapOf<ImageView,Int>(btn1 to 1,btn2 to 2, btn3 to 3, btn4 to 4, btn5 to 5, btn6 to 6, btn7 to 7, btn8 to 8, btn9 to 9, btn10 to 10, btn11 to 11, btn12 to 12, btn13 to 13, btn14 to 14, btn15 to 15, btn16 to 16, btn17 to 17, btn18 to 18, btn19 to 19, btn20 to 20)
        val btnArray = listOf<ImageView>(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20)
        for ((key, value) in numMap) {
            var tempButton = btnArray[key - 1]
            tempButton.setImageResource(btnBackground[value] as Int)
        }

        btnstart.setOnClickListener {
            btnstart.isEnabled = false
            val layoutmain = findViewById<LinearLayout>(R.id.ll_main1)
            val layoutsub1 = findViewById<LinearLayout>(R.id.ll_inputscreen1)
            val layoutsub2 = findViewById<LinearLayout>(R.id.ll_inputscreen2)
            val layoutsub3 = findViewById<LinearLayout>(R.id.ll_inputscreen3)
            val layoutsub4 = findViewById<LinearLayout>(R.id.ll_inputscreen4)
            val layoutsub5 = findViewById<LinearLayout>(R.id.ll_inputscreen5)
            layoutmain.setBackgroundResource(R.drawable.imagebackground)
            layoutmain.dividerPadding = 1
            for (i in btnArray){
                i.setBackgroundResource(R.drawable.imageclickedbackground)
            }
            object : CountDownTimer(30000,1000)
            {
                override fun onFinish() {
                    tvScore.text = "Score : $score"
                    tvTime.text = "Time Left : 0"
                    val ScoreAlert = AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("Score")
                        setMessage("Your Score Is $score out of 10")
                        setPositiveButton("Restart"){dialog, which ->
                            val intent = Intent(this@MainActivity,MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                            finishAffinity()
                        }
                        setNegativeButton("Exit"){dialog, which ->
                            finishAffinity()
                        }
                        setCancelable(false)
                    }
                    ScoreAlert.show()
                    btnstart.isEnabled = true
                }

                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = "Time Left : ${millisUntilFinished/1000}"
                    btn1.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn2.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn3.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn4.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn5.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn6.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn7.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn8.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn9.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn10.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn11.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn12.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn13.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn14.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn15.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn16.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn17.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn18.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn19.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                    btn20.setOnClickListener {
                        if(temp1!=null && temp2!=null)
                        {
                            temp1?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp2?.setBackgroundResource(R.drawable.imageclickedbackground)
                            temp1=null
                            temp2=null
                        }
                        it as ImageView
                        it.setBackgroundResource(R.drawable.imagebackground)
                        if (clicked == 1) {
                            previousbutton = it
                            var tempindex = inputMap.getValue(it as ImageView)
                            prevvalue = numMap.getValue(tempindex)
                            clicked = 2
                        } else if (clicked == 2) {
                            currentbutton = it as ImageView
                            var tempindex = inputMap.getValue(it as ImageView)
                            currvalue = numMap.getValue(tempindex)
                            correct = validInput(previousbutton, currentbutton, prevvalue, currvalue, score)
                            if (correct) {
                                previousbutton!!.isEnabled = false
                                currentbutton!!.isEnabled = false
                                score = score + 1
                                if(score == 10)
                                {
                                    showWinner()
                                }
                            } else {
                                temp1=previousbutton
                                temp2=currentbutton
                            }
                            clicked = 1
                            previousbutton = null
                            currentbutton = null
                            prevvalue = null
                            currvalue = null
                        }
                    }
                }
            }.start()
        }

        btnrestart.setOnClickListener {
            val RestartAlert = AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Restart")
                setMessage("Do You Really Want To Restart The GAme ?")
                setPositiveButton("Yse"){dialog, which ->
                    val intent = Intent(this@MainActivity,MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    finishAffinity()
                }
                setNegativeButton("No"){dialog, which ->

                }
                setCancelable(false)
            }
            RestartAlert.show()
        }


    }

    fun validInput(previousbutton :ImageView? = null ,currentbutton:ImageView? = null,prevvalue:Int? = null,currvalue:Int? = null,score:Int):Boolean{
        if((previousbutton!=null) && (prevvalue!=null) && (currentbutton!=null) && (currvalue!=null)) {
            return prevvalue == currvalue
        }else{
            Toast.makeText(this@MainActivity,"Something Goes Wrong",Toast.LENGTH_LONG).show()
            return false
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun restartInput():MutableMap<Int,Int>{

        val numarray = listOf<Int>(0,1,2,3,4,5,6,7,8,9)
        val numInputArray1 = mutableListOf<Int>(1,2,3,4,5,6,7,8,9,10)
        val numInputArray2 = mutableListOf<Int>(11,12,13,14,15,16,17,18,19,20)
        var numMap = mutableMapOf<Int,Int>(0 to 0)
        for (i in numarray){
            var rand1 = numInputArray1.random()
            var rand2 = numInputArray2.random()
            numInputArray1.remove(rand1)
            numInputArray2.remove(rand2)
            if(!numMap.containsKey(rand1) || !numMap.containsKey(rand2)){
                numMap.put(rand1,i)
                numMap.put(rand2,i)
            }
            else
            {
                Log.i("MainActivity","It Exists")
            }
        }
        numMap.remove(0,0)
        numMap.toSortedMap()
        Log.i("MainActivity",numMap.toString())
        return numMap
    }

    fun showWinner()
    {
        val scoreAlert = AlertDialog.Builder(this@MainActivity).apply{
            setTitle("Score")
            setMessage("The Score of you is 10")
            setPositiveButton("Done"){dialog, which ->

            }
            setNegativeButton("Restart"){dialog, which ->

            }
            setCancelable(false)

        }
        scoreAlert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.maainactivitymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.mn_exit->{
                val exitAlert = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Restart")
                    setMessage("Do You Really Want To Exit The GAme ?")
                    setPositiveButton("Yse"){dialog, which ->
                        finishAffinity()
                    }
                    setNegativeButton("No"){dialog, which ->

                    }
                    setCancelable(false)
                }
                exitAlert.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}