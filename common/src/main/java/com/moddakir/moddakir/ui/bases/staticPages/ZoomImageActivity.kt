package com.moddakir.moddakir.ui.bases.staticPages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.moddakirapps.R
import com.moddakir.moddakir.utils.Utils

class ZoomImageActivity : LocalizationActivity() {
   val utils: Utils = Utils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zoom_image)

    }
    fun start(context: Context, image_url: String?) {
        val starter = Intent(context, ZoomImageActivity::class.java)
        starter.putExtra("IMAGE_URL", image_url)
        context.startActivity(starter)
        val ivImage: ImageView = findViewById(R.id.iv_image)

        val image_url = intent.getStringExtra("IMAGE_URL")
        utils.loadImage(this, image_url, ivImage)
    }

}