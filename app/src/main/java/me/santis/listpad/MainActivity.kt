package me.santis.listpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import me.santis.listpad.Data.DatabaseHelper
import me.santis.listpad.Data.TarefasAdapter
import me.santis.listpad.Model.Categoria
import me.santis.listpad.Model.Item
import me.santis.listpad.Model.Tarefa
import me.santis.listpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var listaTarefas = ArrayList<Tarefa>()

    private lateinit var tarefasAdapter: TarefasAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        setSupportActionBar(binding.Barra)
        binding.cmdNovaTarefa.setOnClickListener {
            val intent = Intent(applicationContext, TarefaAddEditActivity::class.java)
            intent.putExtra("tarefa", Tarefa())
            startActivity(intent)
        }

        // Adiciona Categorias
        db.inserirCategoria(Categoria(0,"Compromisso"))
        db.inserirCategoria(Categoria(1,"Evento"))
        db.inserirCategoria(Categoria(2,"Agenda"))
        updateUI()

    }

    private fun updateUI() {
        listaTarefas = db.listarTarefas()
        tarefasAdapter = TarefasAdapter(listaTarefas)

        val recyclerview = binding.rvTarefas
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = tarefasAdapter

        var listener = object : TarefasAdapter.TarefasListener {
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, TarefaActivity::class.java)
                val t = tarefasAdapter.listaTarefas[pos]
                intent.putExtra("tarefa", t)
                startActivity(intent)
            }
        }
        tarefasAdapter.setClickListener(listener)


    }
}