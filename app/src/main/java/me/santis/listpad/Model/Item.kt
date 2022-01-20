package me.santis.listpad.Model

import java.io.Serializable

class Item (
    var id: Int? = null,
    var descricao: String = "",
    var tar_id: Int = 0,
    var status: Int = 0
) : Serializable