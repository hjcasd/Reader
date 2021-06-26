package com.hjc.library_net.model

data class GankRecommendBean(
    var error: Boolean = false,
    var results: GankRecommendResultBean? = null,
    var category: List<String>
)
