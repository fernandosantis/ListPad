package me.santis.listpad.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.santis.listpad.Model.Item
import me.santis.listpad.R

class ItemsAdapter(val listaItem: ArrayList<Item>) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> () {

    var listener: ItemsListener? = null

    var listaItems = ArrayList<Item>()

    init {
        this.listaItems = listaItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsAdapter.ItemsViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent,false)
        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ItemsViewHolder, position: Int) {
        holder.vhDescricao.text = listaItems[position].descricao
        if (listaItems[position].status == 1) {
           holder.vhCheck.setImageResource(R.drawable.ic_check_1)
        } else {
            holder.vhCheck.setImageResource(R.drawable.ic_check_0)
        }
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

    fun setClickListener(listener: ItemsAdapter.ItemsListener) {
            this.listener = listener
    }


    inner class ItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var vhDescricao = view.findViewById<TextView>(R.id.txtDescricao)
        val vhCheck = view.findViewById<ImageView>(R.id.chkItem)
        val vhDelete = view.findViewById<ImageView>(R.id.cmdExcluirItem)
        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            vhCheck.setOnClickListener {
                if (listaItems[adapterPosition].status == 1) {
                    vhCheck.setImageResource(R.drawable.ic_check_0)
                    listaItems[adapterPosition].status = 0
                    listaItem[adapterPosition].status = 0
                } else {
                    vhCheck.setImageResource(R.drawable.ic_check_1)
                    listaItems[adapterPosition].status = 1
                    listaItem[adapterPosition].status = 1
                }
                val db = DatabaseHelper(it.context)
                db.atualizarItem(listaItems[adapterPosition])
                notifyDataSetChanged()
                db.close()
            }
            vhDelete.setOnClickListener {
                val db = DatabaseHelper(it.context)
                db.apagarItem(listaItems[adapterPosition])
                db.close()
                listaItems.remove(listaItems[adapterPosition])
                notifyDataSetChanged()
                Toast.makeText(it.context, "Item Excluido", Toast.LENGTH_LONG).show()
            }


        }

    }


    // Interface
    interface ItemsListener {
        fun onItemClick(pos: Int)
    }

}