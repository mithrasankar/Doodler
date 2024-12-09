package com.example.doodler

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private lateinit var doodleView: DoodleView
    private lateinit var brushSizeSeekBar: SeekBar
    private lateinit var colorPickerButton: Button
    private lateinit var opacitySeekBar: SeekBar
    private lateinit var clearButton: Button
    private lateinit var eraserButton: Button
    private var isEraserActive = false
    private var lastColor = Color.BLACK
    private var eraserSize = 50f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        doodleView = findViewById(R.id.doodleView)
        brushSizeSeekBar = findViewById(R.id.brush_stroke)
        colorPickerButton = findViewById(R.id.color_picker)
        opacitySeekBar = findViewById(R.id.opacity)
        clearButton = findViewById(R.id.clear)
        eraserButton = findViewById(R.id.eraser_button)

        // seekbar listeners
        brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, from: Boolean) {
                doodleView.setBrushSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        opacitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, from: Boolean) {
                doodleView.setOpacity(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // buttons listeners
        colorPickerButton.setOnClickListener {
            showColorPickerDialog()
        }
        clearButton.setOnClickListener {
            doodleView.clearCanvas()
        }
        eraserButton.setOnClickListener {
            isEraserActive = !isEraserActive
            if (isEraserActive) {
                eraserButton.text = "ERASING"
                lastColor = doodleView.getColor()
                doodleView.setBrushSize(eraserSize)
                doodleView.setColor(Color.WHITE)
            } else {
                eraserButton.text = "ERASER"
                doodleView.setBrushSize(brushSizeSeekBar.progress.toFloat())
                doodleView.setColor(lastColor)
            }
        }
    }
    private fun showColorPickerDialog() {
        val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.CYAN, Color.MAGENTA)
        val colorNames = arrayOf("Red", "Green", "Blue", "Yellow", "Black", "Cyan", "Magenta")

        AlertDialog.Builder(this)
            .setTitle("Pick a Color!")
            .setItems(colorNames) { dialog, which ->
                doodleView.setColor(colors[which])
            }
            .show()
    }
}