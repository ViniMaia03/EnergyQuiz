package com.example.energyquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.energyquiz.databinding.ActivityAtualizacaoCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AtualizacaoCadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityAtualizacaoCadastroBinding.inflate(layoutInflater)
    }

    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    private val bancoDados by lazy {
        FirebaseFirestore.getInstance()
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

        binding.salvarDados.setOnClickListener {
            salvarUsuario()
        }

        binding.atualizarDados.setOnClickListener {
            atualizarUsuario()
        }

        binding.removerDados.setOnClickListener {
            removerUsuario()
        }

        binding.voltarMenuQuiz.setOnClickListener {
            startActivity(Intent(this, Quiz::class.java))
        }
    }

    private fun salvarUsuario() {
        val dados = mapOf(
            "telefone" to binding.telefoneCadastro.text.toString(),
            "endereco" to binding.enderecoCadastro.text.toString()
        )
        val referenciaUsuario = bancoDados.collection("usuarios")
        referenciaUsuario.add(dados).addOnSuccessListener {
            Log.i("db_info", "Salvo com sucesso")
        }.addOnFailureListener {
            Log.i("db_info", "Erro ao salvar")
        }
    }

    private fun atualizarUsuario(){
        val dados = mapOf(
            "telefone" to binding.telefoneCadastro.text.toString(),
            "endereco" to binding.enderecoCadastro.text.toString()
        )

        val idUsuarioAtual = autenticacao.currentUser?.uid

        if (idUsuarioAtual != null) {
            val referenciaUsuario = bancoDados.collection("usuarios").document(idUsuarioAtual)

            referenciaUsuario.update("telefone", binding.telefoneCadastro.text.toString(),
                "endereco", binding.enderecoCadastro.text.toString()).addOnSuccessListener {
                Log.i("db_info", "Atualizado com sucesso")
            }.addOnFailureListener {
                Log.i("db_info", "Erro ao atualizar dados")
            }
        }
    }

    private fun removerUsuario(){
        val idUsuarioAtual = autenticacao.currentUser?.uid

        if (idUsuarioAtual != null) {
            val referenciaUsuario = bancoDados.collection("usuarios").document(idUsuarioAtual)

            referenciaUsuario.delete().addOnSuccessListener {
                Log.i("db_info", "Usuário deletado com sucesso")
            }.addOnFailureListener {
                Log.i("db_info", "Erro ao deletar usuário")
            }
        }
    }
}