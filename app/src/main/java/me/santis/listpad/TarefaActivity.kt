package me.santis.listpad

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import me.santis.listpad.Data.DatabaseHelper
import me.santis.listpad.Data.ItemsAdapter
import me.santis.listpad.Data.TarefasAdapter
import me.santis.listpad.Model.Item
import me.santis.listpad.Model.Tarefa
import me.santis.listpad.databinding.ActivityTarefaBinding

class TarefaActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    var listaItems = ArrayList<Item>()
    private lateinit var tarefa: Tarefa
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var binding: ActivityTarefaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarefaBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa

        binding.cmdNovoItem.setOnClickListener {
            val intent = Intent(applicationContext, ItemAddActivity::class.java)
            intent.putExtra("tarefa", tarefa)
            startActivity(intent)
        }
        binding.cmdEditarTarefa.setOnClickListener {
            val intent = Intent(applicationContext, TarefaAddEditActivity::class.java)
            intent.putExtra("tarefa", tarefa)
            startActivity(intent)
        }

        updateUI()

    }


    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        val txtNome = binding.txtNome
        val txtDescricao = binding.txtDescricao
        val chkStatus = binding.chkTarefa
        val lblCategoria = binding.txtCategoria


        lblCategoria.setText(db.categoriaByID(tarefa.cat_id)?.nome)
        txtNome.setText(tarefa.nome)
        txtDescricao.setText(tarefa.descricao)
        if (tarefa.status == 1) {
            chkStatus.setImageResource(R.drawable.ic_check_1)
            txtNome.setPaintFlags(txtNome.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            chkStatus.setImageResource(R.drawable.ic_check_0)
            txtNome.setPaintFlags(0)
        }


        listaItems = db.listarItems(tarefa)
        itemsAdapter = ItemsAdapter(listaItems)

        val recyclerview = binding.rvItems
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = itemsAdapter

        var listener = object : ItemsAdapter.ItemsListener {
            override fun onItemClick(pos: Int) {
            }
        }
        itemsAdapter.setClickListener(listener)


    }
}