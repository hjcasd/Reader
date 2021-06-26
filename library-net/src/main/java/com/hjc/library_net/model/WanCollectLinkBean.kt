package com.hjc.library_net.model

import java.io.Serializable

data class WanCollectLinkBean(
    var icon: String? = null,
    var id: Int = 0,
    var link: String? = null,
    var name: String? = null,
    var order: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0
) : Serializable