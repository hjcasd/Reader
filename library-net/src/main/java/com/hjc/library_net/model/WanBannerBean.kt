package com.hjc.library_net.model

import java.io.Serializable

data class WanBannerBean(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
) : Serializable
