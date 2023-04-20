package com.example.signinsignupscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    companion object{
        const val KEY1 = "com.example.signinsignupscreen.SignInActivity.mail"
        const val KEY2 = "com.example.signinsignupscreen.SignInActivity.name"
        const val KEY3 = "com.example.signinsignupscreen.SignInActivity.id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val userName = findViewById<TextInputEditText>(R.id.userNameEditText)


        signInButton.setOnClickListener {

            val uniqueId = userName.text.toString()
            if(uniqueId.isNotEmpty()){
                readData(uniqueId)
            }
            else{
                Toast.makeText(this, "Please enter your name",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(uniqueId : String){
        databaseReference = FirebaseDatabase.getInstance().getReference("User")
        databaseReference.child(uniqueId).get().addOnSuccessListener {

            if(it.exists()){
                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                val intentWelcome = Intent(this, WelcomeScreen::class.java)

                intentWelcome.putExtra(KEY1, email.toString())
                intentWelcome.putExtra(KEY2, name.toString())
                intentWelcome.putExtra(KEY3, userId.toString())
                startActivity(intentWelcome)
            }
            else{
                Toast.makeText(this, "User does not exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed,Error in DB",Toast.LENGTH_SHORT).show()
        }
    }
}