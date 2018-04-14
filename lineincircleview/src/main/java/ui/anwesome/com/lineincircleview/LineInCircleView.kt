package ui.anwesome.com.lineincircleview

/**
 * Created by anweshmishra on 14/04/18.
 */

import android.content.*
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class LineInCircleView (ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    override fun onDraw(canvas : Canvas) {

    }
}