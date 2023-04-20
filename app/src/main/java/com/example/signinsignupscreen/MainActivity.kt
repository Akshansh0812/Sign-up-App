package com.example.signinsignupscreen

import android.content.Intent
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signButton = findViewById<Button>(R.id.btnSignUp)
        val etName = findViewById<TextInputEditText>(R.id.etname)
        val etMail = findViewById<TextInputEditText>(R.id.etmail)
        val userPassword = findViewById<TextInputEditText>(R.id.etpassword)
        val userId = findViewById<TextInputEditText>(R.id.etid)


        signButton.setOnClickListener {

            val name = etName.text.toString()
            val mail = etMail.text.toString()
            val password = userPassword.text.toString()
            val uniqueId = userId.text.toString()

            val user = User(name, mail, password, uniqueId)
            database = FirebaseDatabase.getInstance().getReference("User")
            database.child(uniqueId).setValue(user).addOnSuccessListener {
                etName.text?.clear()
                etMail.text?.clear()
                userPassword.text?.clear()
                userId.text?.clear()
                Toast.makeText(this,"user registered",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
            }
        }

        val signInText = findViewById<TextView>(R.id.tvSignIn)
        signInText.setOnClickListener{
            val openSignInActivity = Intent(this, SignInActivity::class.java)
            startActivity(openSignInActivity)
        }
    }
}
