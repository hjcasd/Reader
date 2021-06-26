package com.hjc.library_net.model

data class GankDayBean(
    var title: String? = null,
    var _id: String? = null,
    var createdAt: String? = null,
    var desc: String? = null,
    var publishedAt: String? = null,
    var type: String? = null,
    var url: String? = null,
    var used: Boolean = false,
    var who: String? = null,
    var source: String? = null,
    var images: List<String?>? = null
) {
    companion object {
        const val TYPE_TEXT = 1 //只有文字
        const val TYPE_IMAGE_ONE = 2 //一张图片加文字
        const val TYPE_IMAGE_THREE = 3 //三张图片加文字
        const val TYPE_TITLE = 4 //标题栏
        const val TYPE_IMAGE_ONLY = 5 //标题栏
    }
}
