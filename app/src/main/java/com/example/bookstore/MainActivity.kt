package com.example.bookstore

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bookstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var nottitle=""
    var notmessage=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signupbtn.setOnClickListener {
            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }


        binding.ContinueBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.mapsBtn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.CameraBtn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)




        }

        if(intent.extras !=null){
            for (key in intent.extras!!.keySet()){
                if(key=="title"){
                    nottitle=intent.extras!!.getString("title","")
                }else if(key=="message"){
                    nottitle=intent.extras!!.getString("message","")
                }
            }
        }
        val nottitleTV = findViewById<TextView>(R.id.nottitleTV)
        val notdescTV = findViewById<TextView>(R.id.notdescTV)
        nottitleTV.text=nottitle
        notdescTV.text=notmessage
    }



}