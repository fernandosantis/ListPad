package me.santis.listpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import me.santis.listpad.Data.DatabaseHelper
import me.santis.listpad.Model.Tarefa
import me.santis.listpad.databinding.ActivityTarefaAddEditBinding

class TarefaAddEditActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    private lateinit var tarefa: Tarefa
    private lateinit var binding: ActivityTarefaAddEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarefaAddEditBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa
        val txtNome = binding.txtNome
        val txtDescricao = binding.txtDescricao
        val cboCategoria = binding.cboCategoria
        txtNome.setText(tarefa.nome)
        txtDescricao.setText(tarefa.descricao)

        // Preenche Categoria Spinner
        val categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, db.listarCategorias())
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cboCategoria.adapter = categoriaAdapter
        val categoria = db.categoriaByID(tarefa.cat_id)?.nome
        if (categoria != null) {
            cboCategoria.setSelection(categoriaAdapter.getPosition(categoria))
        }

        binding.cmdCancelar.setOnClickListener {
            finish()
        }

        binding.cmdSalvar.setOnClickListener {
            val idcat = db.categoriaByNome(cboCategoria.getSelectedItem().toString())?.id ?: 0
            val nome = txtNome.text.toString()
            val descricao = txtDescricao.text.toString()
            if (tarefa.id != null) {
                db.atualizarTarefa(Tarefa(tarefa.id,nome,descricao,idcat,0))
                tarefa.nome = nome
                tarefa.descricao = descricao
                tarefa.cat_id = idcat
                tarefa.status = 0
                val intent = Intent(applicationContext, TarefaActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                startActivity(intent)
            } else {
                db.inserirTarefa(Tarefa(tarefa.id,nome,descricao,idcat,0))
            }
            finish()
        }



    }
}