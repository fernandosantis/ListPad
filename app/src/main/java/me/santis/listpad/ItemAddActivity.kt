package me.santis.listpad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.santis.listpad.Data.DatabaseHelper
import me.santis.listpad.Model.Item
import me.santis.listpad.Model.Tarefa
import me.santis.listpad.databinding.ActivityItemAddBinding
import me.santis.listpad.databinding.ActivityTarefaAddEditBinding

class ItemAddActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    private lateinit var tarefa: Tarefa
    private lateinit var binding: ActivityItemAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemAddBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa
        val txtDescricao = binding.txtDescricao

        binding.cmdCancelar.setOnClickListener {
            finish()
        }

        binding.cmdSalvar.setOnClickListener {
            if (tarefa.id != null) {
                db.inserirItem(Item(null,txtDescricao.text.toString(),tarefa.id?:0,0))
            }
            finish()
        }

    }
}