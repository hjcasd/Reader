package com.hjc.library_net.entity

import com.google.gson.annotations.SerializedName

data class GankRecommendResultBean(
    var Android: List<GankDayBean>? = null,
    var App: List<GankDayBean>? = null,
    var iOS: List<GankDayBean>? = null,

    @SerializedName("前端")
    var front: List<GankDayBean>? = null,

    @SerializedName("休息视频")
    var rest: List<GankDayBean>? = null,

    @SerializedName("拓展资源")
    var extra: List<GankDayBean>? = null,

    @SerializedName("瞎推荐")
    var recommend: List<GankDayBean>? = null,

    @SerializedName("福利")
    var welfare: List<GankDayBean>? = null,
)
