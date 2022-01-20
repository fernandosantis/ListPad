package me.santis.listpad.Data

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.santis.listpad.Model.Tarefa
import me.santis.listpad.R

class TarefasAdapter(val listaTarefa: ArrayList<Tarefa>) : RecyclerView.Adapter<TarefasAdapter.TarefasViewHolder> () {

    var listener: TarefasListener? = null

    var listaTarefas = ArrayList<Tarefa>()

    init {
        this.listaTarefas = listaTarefa
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarefasAdapter.TarefasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent,false)
        return TarefasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefasAdapter.TarefasViewHolder, position: Int) {
        holder.vhNome.text = listaTarefas[position].nome
        if (listaTarefas[position].status == 1) {
            holder.vhCheck.setImageResource(R.drawable.ic_check_1)
            holder.vhNome.setPaintFlags(holder.vhNome.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            holder.vhCheck.setImageResource(R.drawable.ic_check_0)
            holder.vhNome.setPaintFlags(0)
        }
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

    fun setClickListener(listener: TarefasAdapter.TarefasListener) {
            this.listener = listener
    }


    inner class TarefasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var vhNome = view.findViewById<TextView>(R.id.txtDescricao)
        val vhCheck = view.findViewById<ImageView>(R.id.chkItem)
        val vhDelete = view.findViewById<ImageView>(R.id.cmdExcluirItem)

        init {
            vhNome.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            vhCheck.setOnClickListener {
                if (listaTarefas[adapterPosition].status == 1) {
                    vhCheck.setImageResource(R.drawable.ic_check_0)
                    listaTarefas[adapterPosition].status = 0
                    listaTarefa[adapterPosition].status = 0
                } else {
                    vhCheck.setImageResource(R.drawable.ic_check_1)
                    listaTarefas[adapterPosition].status = 1
                    listaTarefa[adapterPosition].status = 1
                }
                val db = DatabaseHelper(it.context)
                db.atualizarTarefa(listaTarefas[adapterPosition])
                notifyDataSetChanged()
                db.close()
            }
            vhDelete.setOnClickListener {
                val db = DatabaseHelper(it.context)
                db.apagarItemsTarefa(listaTarefas[adapterPosition])
                db.apagarTarefa(listaTarefas[adapterPosition])
                db.close()
                listaTarefas.remove(listaTarefas[adapterPosition])
                notifyDataSetChanged()
                Toast.makeText(it.context, "Tarefa Excluida", Toast.LENGTH_LONG).show()
            }

        }

    }


    // Interface
    interface TarefasListener {
        fun onItemClick(pos: Int)
    }

}