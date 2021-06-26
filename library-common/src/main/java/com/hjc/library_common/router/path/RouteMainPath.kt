package com.hjc.library_common.router.path

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: Main模块路由
 */
object RouteMainPath {

    private const val MODULE_MAIN = "/module_main"

    // Activity
    object Activity{

        // MainActivity
        const val URL_MAIN_ACTIVITY = "$MODULE_MAIN/activity/main"

        // Splash页面
        const val URL_SPLASH = "$MODULE_MAIN/activity/splash"

        // Web页面
        const val URL_WEB = "$MODULE_MAIN/activity/web"

        // 扫码页面
        const val URL_SCAN = "$MODULE_MAIN/activity/scan"

        // 收藏页面
        const val URL_COLLECT = "$MODULE_MAIN/activity/collect"

        // 搜索页面
        const val URL_SEARCH = "$MODULE_MAIN/activity/search"

    }


    // Fragment
    object Fragment{

        // MainFragment
        const val URL_MAIN_FRAGMENT = "$MODULE_MAIN/fragment/main"

        // DrawerFragment
        const val URL_DRAWER = "$MODULE_MAIN/fragment/drawer"

        // TestFragment
        const val URL_TEST = "$MODULE_MAIN/fragment/test"

        // CollectArticleFragment
        const val URL_COLLECT_ARTICLE = "$MODULE_MAIN/fragment/collect/article"

        // CollectLinkFragment
        const val URL_COLLECT_LINK = "$MODULE_MAIN/fragment/collect/link"

        // SearchHistoryFragment
        const val URL_SEARCH_HISTORY = "$MODULE_MAIN/fragment/search/history"

        // SearchResultFragment
        const val URL_SEARCH_RESULT = "$MODULE_MAIN/fragment/search/result"

    }

}