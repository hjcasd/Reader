package com.hjc.library_net.entity

import java.io.Serializable

/**
 * 项目分类
 */
data class WanClassifyBean(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
) : Serializable