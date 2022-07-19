package com.fromto.write.model

data class mailDTO(
    var title : String? = null,
    var explain : String? = null,
    var imageUrl : String? = null,
    var uid : String? = null,
    var userId : String? =  null,
    var timestamp : Long? =  null,
    var favoriteCount : Int =  0,
    var favorites: MutableMap<String, Boolean> = HashMap())
