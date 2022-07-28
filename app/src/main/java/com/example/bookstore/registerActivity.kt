package com.example.bookstore

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.bookstore.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class registerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding

    private lateinit var progressDialog:ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth


    private var email = ""
    private var password=""









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alreadyhaveaccnt = findViewById<TextView>(R.id.signInTv)

        alreadyhaveaccnt.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account..")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerBtn.setOnClickListener {
            if(validateData()){
                firebaseRegister()
            }

        }

        binding.signInTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }




    }

    private fun validateData() :Boolean {

        var valid =true

        email=binding.emailET.text.toString().trim()
        password=binding.passwordET.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailET.error = "Invalid Email Format"
            valid =false
        }else if(TextUtils.isEmpty(password)){
            binding.passwordET.error = "Please Enter Password"
            valid =false
        }else if(password.length<6){
            binding.passwordET.error = "Password Must Be At Least 6 Characters Long"
            valid =false
        }
        return valid


    }

    private fun firebaseRegister() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser?.email
                Toast.makeText(this, "Account Created With email $email", Toast.LENGTH_SHORT).show()

                 startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this, "Sign up Failed Due To ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}