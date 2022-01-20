package me.santis.listpad.Model

import java.io.Serializable

class Tarefa (
    var id: Int? = null,
    var nome: String = "",
    var descricao: String = "",
    var cat_id: Int? = null,
    var status: Int = 0
): Serializable
