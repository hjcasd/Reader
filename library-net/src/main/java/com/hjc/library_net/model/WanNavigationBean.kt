package com.hjc.library_net.model

import java.io.Serializable

data class WanNavigationBean(
    var articles: MutableList<WanArticleBean>,
    var cid: Int,
    var name: String,
     var isSelected: Boolean
) : Serializable