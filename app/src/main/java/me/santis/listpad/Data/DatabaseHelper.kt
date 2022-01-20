package me.santis.listpad.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import me.santis.listpad.Model.Categoria
import me.santis.listpad.Model.Item
import me.santis.listpad.Model.Tarefa

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NOME, null, DB_VERSAO){

    companion object {
        private val DB_NOME = "listpad.db"
        private val DB_VERSAO = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        // Tabela Categorias
        // p0?.setForeignKeyConstraintsEnabled(true)
        p0?.execSQL("CREATE TABLE categorias (cat_id INTEGER PRIMARY KEY AUTOINCREMENT, cat_nome TEXT NOT NULL UNIQUE)")
        // Tabela Tarefas
        p0?.execSQL("CREATE TABLE tarefas (tar_id INTEGER PRIMARY KEY AUTOINCREMENT, tar_nome TEXT NOT NULL UNIQUE, tar_descricao TEXT, cat_id INTEGER NOT NULL, tar_status INTEGER DEFAULT 0)")
        // Tabela Items
        p0?.execSQL("CREATE TABLE items (ite_id INTEGER PRIMARY KEY AUTOINCREMENT, ite_descricao TEXT NOT NULL UNIQUE, tar_id INTEGER NOT NULL, ite_status INTEGER DEFAULT 0, FOREIGN KEY (tar_id) REFERENCES tarefas(tar_id) ON DELETE CASCADE ON UPDATE NO ACTION)")
     }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /*A Fazer*/
    }

    // Categorias

    fun inserirCategoria(categoria: Categoria): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("cat_id", categoria.id)
        values.put("cat_nome", categoria.nome)
        val result = db.insert("categorias", null, values)
        db.close()
        return result
    }

    fun apagarCategoria(categoria: Categoria): Int {
        val db = this.writableDatabase
        val result = db.delete("categorias", "cat_id=?", arrayOf(categoria.id.toString()))
        db.close()
        return result
    }

    fun listarCategorias(): List<String> {
        val listaCategorias: MutableList<String> = mutableListOf()
        val query = "SELECT * FROM categorias ORDER BY cat_id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            listaCategorias.add(cursor.getString(1))
        }
        cursor.close()
        db.close()
        return listaCategorias
    }

    fun categoriaByID(id: Int?): Categoria? {
        val query = "SELECT * FROM categorias WHERE cat_id = $id"
        val db = this.readableDatabase
        db.rawQuery(query, null).use {
            if (it.moveToFirst()) {
                return Categoria(it.getInt(0),
                    it.getString(1))
            }
        }
        db.close()
        return null
    }
    fun categoriaByNome(nome: String?): Categoria? {
        val query = "SELECT * FROM categorias WHERE cat_nome = '$nome'"
        val db = this.readableDatabase
        db.rawQuery(query, null).use {
            if (it.moveToFirst()) {
                return Categoria(it.getInt(0),
                    it.getString(1))
            }
        }
        db.close()
        return null
    }
    // Tarefas

    fun inserirTarefa(tarefa: Tarefa): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("tar_id", tarefa.id)
        values.put("tar_nome", tarefa.nome)
        values.put("tar_descricao", tarefa.descricao)
        values.put("cat_id", tarefa.cat_id)
        values.put("tar_status", tarefa.status)
        val result = db.insert("tarefas", null, values)
        db.close()
        return result
    }

    fun apagarTarefa(tarefa: Tarefa): Int {
        val db = this.writableDatabase
        val result = db.delete("tarefas", "tar_id=?", arrayOf(tarefa.id.toString()))
        db.close()
        return result
    }

    fun atualizarTarefa(tarefa: Tarefa): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("tar_id", tarefa.id)
        values.put("tar_nome", tarefa.nome)
        values.put("tar_descricao", tarefa.descricao)
        values.put("cat_id", tarefa.cat_id)
        values.put("tar_status", tarefa.status)
        val result = db.update("tarefas", values, "tar_id=?", arrayOf(tarefa.id.toString()))
        db.close()
        return result
    }

    fun listarTarefas(): ArrayList<Tarefa> {
        val listaTarefas = ArrayList<Tarefa>()
        val query = "SELECT * FROM tarefas ORDER BY tar_status DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val t = Tarefa(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4)
            )
            listaTarefas.add(t)
        }
        cursor.close()
        db.close()
        return listaTarefas
    }

    fun buscaTarefaID(id: Int?): Tarefa? {
        val query = "SELECT * FROM tarefas WHERE tar_id = $id"
        val db = this.readableDatabase
        db.rawQuery(query, null).use {
            if (it.moveToFirst()) {
                return Tarefa(it.getInt(0),
                    it.getString(1),
                    it.getString(2),
                    it.getInt(3),
                    it.getInt(4)
                )
            }
        }
        db.close()
        return null
    }


    // Items
    fun inserirItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ite_id",item.id)
        values.put("ite_descricao", item.descricao)
        values.put("tar_id", item.tar_id)
        values.put("ite_status", item.status)
        val result = db.insert("items", null, values)
        db.close()
        return result
    }

    fun apagarItem(item: Item): Int {
        val db = this.writableDatabase
        val result = db.delete("items", "ite_id=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

    fun apagarItemsTarefa(tarefa: Tarefa): Int {
        val db = this.writableDatabase
        val result = db.delete("items", "tar_id=?", arrayOf(tarefa.id.toString()))
        db.close()
        return result
    }

    fun atualizarItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ite_id", item.id)
        values.put("ite_descricao", item.descricao)
        values.put("tar_id", item.tar_id)
        values.put("ite_status", item.status)
        val result = db.update("items", values, "ite_id=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

    fun listarItems(tarefa: Tarefa): ArrayList<Item> {
        val listaItems = ArrayList<Item>()
        val query = "SELECT * FROM items WHERE tar_id=${tarefa.id} ORDER BY ite_status DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val i = Item(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            listaItems.add(i)
        }
        cursor.close()
        db.close()
        return listaItems
    }

}