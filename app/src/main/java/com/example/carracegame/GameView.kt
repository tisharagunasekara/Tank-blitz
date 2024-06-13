package com.example.carracegame
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs


@SuppressLint("ViewConstructor")
class GameView( c :Context, var gameTask :GameTask):View(c)
{
    private var myPaint: Paint? =null
    private var speed =1
    private var time = 0
    private var score =0
    private var  tank = 0
    private var bomb = ArrayList<HashMap<String,Any>>()

    private var viewWidth = 0
    private var viewHeight = 0

    init {
        myPaint = Paint()
    }


    @SuppressLint("UseCompatLoadingForDrawables", "DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time% 700 <10 + speed){
            val map =HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            bomb.add(map)

        }
        time += 10 + speed
        val carWidth = viewWidth/5
        val carHeight = carWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.tank,null)

        d.setBounds(
            tank * viewWidth/ 3 + viewWidth / 15+ 25,
            viewHeight -2 -carHeight,
            tank * viewWidth / 3 + viewWidth /15 + carWidth - 25 ,
            viewHeight - 2
        )
        d.draw(canvas)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in bomb.indices){
            try {
                val carX = bomb[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                val carY = time - bomb[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.bomb,null)

                d2.setBounds(
                    carX + 25 , carY - carHeight , carX + carWidth -25 , carY
                )
                d2.draw(canvas)
                if(bomb[i]["lane"] as Int == tank){
                    if (carY> viewHeight - 2 - carHeight
                        && carY < viewHeight - 2){
                        gameTask.closeGame(score)
                    }
                    if (carY>viewHeight + carHeight)
                    {
                        bomb.removeAt(i)
                        score++
                        speed = 1 + abs(score/8)
                        if(score > highScore){
                            highScore = score
                        }

                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()

            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score",80f,80f,myPaint!!)
        canvas.drawText("Speed,$speed",380f,80f,myPaint!!)
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 < viewWidth/2){
                    if(tank>0){
                        tank--
                    }
                }
                if(x1>viewWidth/2){
                    if (tank<2){
                        tank++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{}

        }
        return true
    }
}
