package com.example.burgerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
//getting information from the mainActicity class and putting it in the detailActivity

        name_text.text= "Name: " +intent.getStringExtra(MainActivity.name_item)
        description_text.text= "Description: " +intent.getStringExtra(MainActivity.desription_item)
       profile_img.setImageResource(intent.getIntExtra(MainActivity.image_id, R.drawable.fanta))

    }


}