package com.hjc.library_common.router.path

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: Wan模块路由
 */
object RouteWanPath {

    private const val MODULE_WAN = "/module_wan"


    // Activity
    object Activity{

        // WanActivity
        const val URL_WAN_ACTIVITY = "$MODULE_WAN/activity/wan"

        // TagActivity
        const val URL_TAG = "$MODULE_WAN/activity/tag"

    }


    // Fragment
    object Fragment{

        // WanFragment
        const val URL_WAN_FRAGMENT = "$MODULE_WAN/fragment/wan"

        // WanFragment
        const val URL_NAVIGATION = "$MODULE_WAN/fragment/navigation"

        // WanFragment
        const val URL_PLAY= "$MODULE_WAN/fragment/play"

        // WanFragment
        const val URL_TREE = "$MODULE_WAN/fragment/tree"

    }

}