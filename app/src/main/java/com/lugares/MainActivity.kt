package com.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Objeto de autentificación por firebase
    private lateinit var auth : FirebaseAuth

    // Objeto de acceso a vista
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de autentificación
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // Objeto de interacción con botón de registro
        binding.btRegister.setOnClickListener {registerUser()}
        binding.btLogin.setOnClickListener {loginUser()}
    }

    private fun registerUser() {
        // Se recupera información de autentificación
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        // Se realiza el registro mediante Firebase
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){task->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateLoggedUser(user)
            }else{
                Toast.makeText(baseContext, "Fallo al registrar usuario", Toast.LENGTH_LONG).show()
                updateLoggedUser(null)
            }
        }
    }

    private fun loginUser() {
        // Se recupera información de autentificación
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        // Se realiza el registro mediante Firebase
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){task->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateLoggedUser(user)
            }else{
                Toast.makeText(baseContext, "Fallo al ingresar usuario", Toast.LENGTH_LONG).show()
                updateLoggedUser(null)
            }
        }
    }

    private fun updateLoggedUser(user: FirebaseUser?) {
        // Cheaquea si existe un usuario autentificado
        if(user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    // Verificación de usuarios autenticados
    public override fun onStart() {
        super.onStart()
        updateLoggedUser(auth.currentUser)
    }

}
