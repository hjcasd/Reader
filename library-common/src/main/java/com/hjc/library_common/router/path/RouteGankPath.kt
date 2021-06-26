package com.hjc.library_common.router.path

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: Gank模块路由
 */
object RouteGankPath {

    private const val MODULE_GANK = "/module_gank"


    // Activity
    object Activity{

        // GankActivity
        const val URL_GANK_ACTIVITY = "$MODULE_GANK/activity/gank"

    }


    // Fragment
    object Fragment{

        // GankFragment
        const val URL_GANK_FRAGMENT = "$MODULE_GANK/fragment/gank"

        // RecommendFragment
        const val URL_RECOMMEND = "$MODULE_GANK/fragment/recommend"

        // WelfareFragment
        const val URL_WELFARE = "$MODULE_GANK/fragment/welfare"

        // CustomFragment
        const val URL_CUSTOM= "$MODULE_GANK/fragment/custom"

    }

}