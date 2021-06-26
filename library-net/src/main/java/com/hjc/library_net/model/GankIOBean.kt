package com.hjc.library_net.model

data class GankIOBean(
    var status: Int = 0,
    var data: MutableList<GankDayBean>?
)
