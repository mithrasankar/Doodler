package com.example.doodler

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
class DoodleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private val path = Path()
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //  all paths
        // current path
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        canvas.drawPath(path, paint)
    }

    fun getColor(): Int {
        return paint.color
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                canvas.drawPath(path, paint)
                path.reset()
            }
        }
        invalidate()
        return true
    }

    fun setBrushSize(size: Float) {
        paint.strokeWidth = size
    }

    // brush color
    fun setColor(color: Int) {
        paint.color = color
    }

    fun clearCanvas() {
        path.reset()
        canvas.drawColor(Color.WHITE)
        invalidate()
    }

    fun setOpacity(opacity: Int) {
        paint.alpha = opacity
    }

}
