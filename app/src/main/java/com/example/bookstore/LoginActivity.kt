package com.example.bookstore

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.bookstore.databinding.ActivityLoginBinding
import com.example.bookstore.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in..")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        setContentView(R.layout.activity_login)



        binding.signUpTv.setOnClickListener{

            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }


        binding.loginBtn.setOnClickListener{
            if(validateData()){
                firebaseLogin()
            }

            val intent = Intent(this, BooksActivity::class.java)
            startActivity(intent)

        }


    }

    private fun validateData() :Boolean{

        var valid =true

        email = binding.emailET.text.toString().trim()
        password = binding.passwordET.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailET.error = "Invalid E-mail Format"
            valid= false
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordET.error="Please Enter Password"
            valid = false
        }else{
            firebaseLogin()
        }
        return valid
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser?.email
                Toast.makeText( this,"LoggedIn As $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, BooksActivity::class.java))
                finish()

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText( this,"Login Failed Due To ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            startActivity(Intent(this, BooksActivity::class.java))
            finish()
        }
    }
}