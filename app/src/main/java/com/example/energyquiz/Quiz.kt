package com.example.energyquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.energyquiz.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Quiz : AppCompatActivity() {

    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }

    private val questoes = arrayOf("Quais dessas fontes de energia é renovável?",
        "Por que as energias renováveis são importantes para o futuro do planeta?",
        "Qual dessas características é verdadeira sobre a energia solar?")

    private val opcoes = arrayOf(arrayOf("Energia Solar", "Carvão", "Petróleo"),
        arrayOf("Porque são mais baratas que todas as outras formas de energia.", "Porque ajudam a reduzir a poluição e o uso de recursos limitados.", "Porque podem ser usadas apenas em dias ensolarados."),
        arrayOf("Ela depende do carvão para funcionar.", "Ela gera energia apenas quando há vento.", "É uma fonte que não emite poluentes ao ser utilizada."))

    private val respostas = arrayOf(0, 1, 2)

    private var currentQuestaoIndex = 0
    private var placar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayQuestao()

        binding.opcao1Botao.setOnClickListener{
            checarResposta(0)
        }
        binding.opcao2Botao.setOnClickListener{
            checarResposta(1)
        }
        binding.opcao3Botao.setOnClickListener{
            checarResposta(2)
        }

        binding.restartBotao.setOnClickListener{
            restartQuiz()
        }

        binding.botaoAtualizacaoCadastro.setOnClickListener{
            atualizarCadastroPagina()
        }
    }

    private fun respostaCorretaCor(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.opcao1Botao.setBackgroundColor(Color.GREEN)
            1 -> binding.opcao2Botao.setBackgroundColor(Color.GREEN)
            2 -> binding.opcao3Botao.setBackgroundColor(Color.GREEN)
        }
    }

    private fun respostaErradoCor(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.opcao1Botao.setBackgroundColor(Color.RED)
            1 -> binding.opcao2Botao.setBackgroundColor(Color.RED)
            2 -> binding.opcao3Botao.setBackgroundColor(Color.RED)
        }
    }

    private fun resetBotaoCor() {
        binding.opcao1Botao.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.opcao2Botao.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.opcao3Botao.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun resultados() {
        Toast.makeText(this, "Seu pontuação: $placar de ${questoes.size}", Toast.LENGTH_LONG).show()
        binding.restartBotao.isEnabled = true
    }

    private fun displayQuestao() {
        binding.questaoTexto.text = questoes[currentQuestaoIndex]
        binding.opcao1Botao.text = opcoes[currentQuestaoIndex][0]
        binding.opcao2Botao.text = opcoes[currentQuestaoIndex][1]
        binding.opcao3Botao.text = opcoes[currentQuestaoIndex][2]
        resetBotaoCor()
    }

    private fun checarResposta(opcaoSelecionadaIndex: Int) {
        val respostaCorretaIndex = respostas[currentQuestaoIndex]

        if (opcaoSelecionadaIndex == respostaCorretaIndex){
            placar++
            respostaCorretaCor(opcaoSelecionadaIndex)
        } else {
            respostaErradoCor(opcaoSelecionadaIndex)
        }

        if (currentQuestaoIndex < questoes.size - 1) {
            currentQuestaoIndex++
            binding.questaoTexto.postDelayed({displayQuestao()}, 1000)
        } else {
            resultados()
        }
    }

    private fun restartQuiz(){
        currentQuestaoIndex = 0
        placar = 0
        displayQuestao()
        binding.restartBotao.isEnabled = false
    }

    private fun atualizarCadastroPagina(){
        startActivity(Intent(this, AtualizacaoCadastro::class.java))
    }
}
