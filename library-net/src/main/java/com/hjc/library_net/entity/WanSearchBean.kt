package com.hjc.library_net.entity

import java.io.Serializable

data class WanSearchBean(
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var visible: Int
) : Serializable