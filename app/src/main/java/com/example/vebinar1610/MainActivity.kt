package com.example.vebinar1610

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.text)

        ValueAnimator.ofObject(
            GradientArgbEvaluator,
            intArrayOf(Color.MAGENTA, Color.MAGENTA, Color.MAGENTA),
            intArrayOf(Color.MAGENTA, Color.MAGENTA, Color.BLUE),
            intArrayOf(Color.MAGENTA, Color.BLUE, Color.BLACK),
            intArrayOf(Color.BLUE, Color.BLACK, Color.RED),
            intArrayOf(Color.BLACK, Color.RED, Color.GREEN),
            intArrayOf(Color.BLACK, Color.GREEN, Color.BLUE),
            intArrayOf(Color.GREEN, Color.BLUE, Color.CYAN),
            intArrayOf(Color.BLUE, Color.CYAN, Color.MAGENTA),
            intArrayOf(Color.CYAN, Color.YELLOW, Color.MAGENTA),
        ).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            duration =  3000
            addUpdateListener {
                val shader = LinearGradient(
                    0f, 0f,
                    textView.paint.measureText(textView.text.toString()),
                    textView.textSize,
                    it.animatedValue as IntArray,
                    null,
                    Shader.TileMode.CLAMP
                )
                textView.paint.shader = shader
                textView.invalidate()
            }
            start()
        }
    }


    object GradientArgbEvaluator : TypeEvaluator<IntArray> {

        private val argbEvaluator = ArgbEvaluator()
        override fun evaluate(
            fraction: Float,
            startValue: IntArray?,
            endValue: IntArray?
        ): IntArray {
            return startValue!!.mapIndexed { index, item ->
                argbEvaluator.evaluate(fraction, item, endValue!![index]) as Int
            }.toIntArray()
        }




       /*  val textShader = LinearGradient(
             0f, 0f,
             textView.paint.measureText(textView.text.toString()),
             textView.textSize,
             intArrayOf(Color.YELLOW, Color.GREEN, Color.BLUE),
             null,
             Shader.TileMode.CLAMP
         )
         textView.paint.shader = textShader
         textView.invalidate()*/
    }
}