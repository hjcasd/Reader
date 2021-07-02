package com.hjc.library_net.entity

import java.io.Serializable

data class WanSystemBean(
    var children: MutableList<WanClassifyBean>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
) : Serializable