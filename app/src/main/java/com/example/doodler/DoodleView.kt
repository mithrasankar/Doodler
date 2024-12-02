package com.example.doodler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DoodleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Store paths with their respective paints
    private val paths = mutableListOf<Pair<Path, Paint>>()

    // Current paint and path
    private var currentPaint = Paint().apply {
        color = resources.getColor(R.color.purple_200) // Default to purple
        strokeWidth = 10f // Default stroke size
        isAntiAlias = true
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private var currentPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw all paths with their corresponding paints
        for ((path, paint) in paths) {
            canvas.drawPath(path, paint)
        }

        // Draw the current path
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Create a new path for the current stroke
                currentPath = Path().apply { moveTo(x, y) }
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                // Add the completed path to the list along with its paint
                paths.add(Pair(currentPath, Paint(currentPaint)))
            }
        }

        invalidate() // Redraw the view
        return true
    }

    // Change brush size
    fun setBrushSize(size: Float) {
        currentPaint.strokeWidth = size
    }

    // Change brush color
    fun setColor(color: Int) {
        currentPaint.color = color
    }

    // Clear the canvas
    fun clearCanvas() {
        paths.clear() // Clear all saved paths
        currentPath.reset() // Reset the current path
        invalidate() // Redraw the view
    }
}
