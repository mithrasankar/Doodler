package com.example.doodler

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doodleView = findViewById<DoodleView>(R.id.doodleView)
        val clearButton = findViewById<Button>(R.id.clear)
        val brushSizeSeekBar = findViewById<SeekBar>(R.id.brush_stroke)
        val color1Button = findViewById<Button>(R.id.color1)
        val color2Button = findViewById<Button>(R.id.color2)

        // default brush
        doodleView.setBrushSize(brushSizeSeekBar.progress.toFloat())

        // brush size listener
        brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val scaledSize = (10 + progress / 2f)
                doodleView.setBrushSize(scaledSize)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // buttons listeners
        color1Button.setOnClickListener {
            doodleView.setColor(resources.getColor(R.color.purple_200))
        }

        color2Button.setOnClickListener {
            doodleView.setColor(resources.getColor(R.color.teal_700))
        }

        clearButton.setOnClickListener {
            doodleView.clearCanvas()
        }
    }
}
