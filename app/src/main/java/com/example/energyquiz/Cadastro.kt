package com.example.energyquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.energyquiz.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth

class Cadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
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

        binding.botaoCadastrado.setOnClickListener{
            cadastroUsuario()
        }

        binding.botaoLogar.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun cadastroUsuario() {
        val email = "viniciusmsantos02@gmail.com"
        val senha = "123456"

        val autenticacao = FirebaseAuth.getInstance()

        autenticacao.createUserWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener { authResult->
            val email = authResult.user?.email
            val id = authResult.user?.uid

            binding.textoCadastrado.text = "Sucesso: $id - $email"
        }.addOnFailureListener{exception->
            val mensagemErro = exception.message
            binding.textoCadastrado.text = "Error: $mensagemErro"
        }
    }
}