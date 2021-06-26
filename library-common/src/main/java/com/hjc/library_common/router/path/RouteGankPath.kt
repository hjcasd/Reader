package com.hjc.library_common.router.path

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: Gank模块路由
 */
object RouteGankPath {

    private const val MODULE_GANK = "/module_gank"

    //=========================================Activity========================================

    // GankActivity
    const val URL_ACTIVITY_GANK = "$MODULE_GANK/activity/gank"


    //=========================================Fragment========================================

    // GankFragment
    const val URL_FRAGMENT_GANK = "$MODULE_GANK/fragment/gank"

    // RecommendFragment
    const val URL_FRAGMENT_RECOMMEND = "$MODULE_GANK/fragment/recommend"

    // WelfareFragment
    const val URL_FRAGMENT_WELFARE = "$MODULE_GANK/fragment/welfare"

    // CustomFragment
    const val URL_FRAGMENT_CUSTOM= "$MODULE_GANK/fragment/custom"

}