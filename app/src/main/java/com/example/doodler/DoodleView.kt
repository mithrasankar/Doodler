package com.example.doodler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class DoodleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // paths
    private val paths = mutableListOf<Pair<Path, Paint>>()

    // current
    private var currentPaint = Paint().apply {
        color = resources.getColor(R.color.purple_200)
        strokeWidth = 10f
        isAntiAlias = true
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private var currentPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //  all paths
        for ((path, paint) in paths) {
            canvas.drawPath(path, paint)
        }
        // current path
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // new path for current stroke
                currentPath = Path().apply { moveTo(x, y) }
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                // add the completed path with its color
                paths.add(Pair(currentPath, Paint(currentPaint)))
            }
        }
        invalidate()
        return true
    }
    fun setBrushSize(size: Float) {
        currentPaint.strokeWidth = size
    }
    // brush color
    fun setColor(color: Int) {
        currentPaint.color = color
    }
    fun clearCanvas() {
        paths.clear()
        currentPath.reset()
        invalidate()
    }
}
