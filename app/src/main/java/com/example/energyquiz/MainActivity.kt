package com.example.energyquiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.energyquiz.databinding.ActivityMainBinding
import com.example.energyquiz.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.botaoCadastrar.setOnClickListener{
            telaCadastro()
        }

        binding.botaoQuiz.setOnClickListener{
            telaQuiz()
        }
    }

    private fun telaQuiz() {
        val email = "viniciusmsantos02@gmail.com"
        val senha = "123456"

        autenticacao.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
            authResult -> startActivity(Intent(this, Quiz::class.java))
        }.addOnFailureListener{exception->
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Verifique seu e-mail e senha")
                .setNegativeButton("Ok"){dialog, posicao->}
                .create().show()
        }
    }

    private fun telaCadastro() {
        startActivity(Intent(this, Cadastro::class.java))
    }
}