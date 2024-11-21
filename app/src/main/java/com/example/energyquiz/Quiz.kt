package com.example.energyquiz

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.energyquiz.databinding.ActivityQuizBinding

class Quiz : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private val questoes = arrayOf("Questão A", "Questão B", "Questão C")

    private val opcoes = arrayOf(arrayOf("A", "B", "C"),
        arrayOf("D", "E", "F"),
        arrayOf("G", "H", "I"))

    private val respostas = arrayOf(0, 1, 2)

    private var currentQuestaoIndex = 0
    private var placar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
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
            respostaCorretaCor(opcaoSelecionadaIndex)
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
}
