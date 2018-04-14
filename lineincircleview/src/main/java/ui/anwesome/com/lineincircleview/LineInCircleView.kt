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

    data class State (var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {

        fun animate (updatecb : () -> Unit) {
            if (animated) {
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop () {
            if (animated) {
                animated = false
            }
        }
    }

    data class LineInCircle (var i : Int, private val state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            paint.color = Color.WHITE
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val r : Float = Math.min(w, h)/3
            paint.strokeWidth = Math.min(w, h)/50
            paint.style = Paint.Style.STROKE
            canvas.save()
            canvas.translate(w/2, h/2)
            canvas.drawCircle(0f, 0f, r, paint)
            val updated_deg = 180 * state.scale
            val path = Path()
            for (i in 0..1) {
                val deg = 90f + (1 - 2 * i) * updated_deg
                val x : Float = r * Math.cos(deg * Math.PI/180).toFloat()
                val y : Float = r * Math.sin(deg * Math.PI/180).toFloat()
                if (i == 0) {
                    path.moveTo(x, y)
                }
                else {
                    path.lineTo(x, y)
                }
            }
            canvas.drawPath(path, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer (var view : LineInCircleView) {

        private val animator : Animator = Animator(view)

        private final val lineInCircle = LineInCircle(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            lineInCircle.draw(canvas, paint)
            animator.animate {
                lineInCircle.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            lineInCircle.startUpdating {
                animator.stop()
            }
        }
    }
}