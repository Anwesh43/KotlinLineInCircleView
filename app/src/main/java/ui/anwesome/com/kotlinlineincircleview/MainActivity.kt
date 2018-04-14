package ui.anwesome.com.kotlinlineincircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.lineincircleview.LineInCircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LineInCircleView.create(this)
    }
}
