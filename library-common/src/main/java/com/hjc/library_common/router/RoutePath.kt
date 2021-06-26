package com.hjc.library_common.router

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: 管理路由路径
 */
object RoutePath {

    // 主模块
    object Main {
        private const val MODULE_MAIN = "/module_main"

        // Splash页面
        const val SPLASH = "$MODULE_MAIN/splash"

        // 主界面
        const val MAIN = "$MODULE_MAIN/main"

        // Web页面
        const val WEB = "$MODULE_MAIN/web"

        // 扫码页面
        const val SCAN = "$MODULE_MAIN/scan"

        // 收藏页面
        const val COLLECT = "$MODULE_MAIN/collect"

        // 搜索页面
        const val SEARCH = "$MODULE_MAIN/search"
    }

    object Login {
        private const val MODULE_LOGIN = "/module_login"

        // 登录页面
        const val LOGIN = "$MODULE_LOGIN/login"

        // 注册页面
        const val REGISTER = "$MODULE_LOGIN/register"
    }


    // 玩安卓模块
    object Wan {
        private const val MODULE_WAN = "/module_wan"

        // Wan页面
        const val WAN_PAGE = "$MODULE_WAN/page"

        // WanFragment
        const val WAN_FRAGMENT = "$MODULE_WAN/fragment"

        // WanFragment
        const val WAN_TAG = "$MODULE_WAN/tag"
    }


    // 干活模块
    object Gank {
        private const val MODULE_GANK = "/module_gank"

        // Frame页面
        const val GANK_PAGE = "$MODULE_GANK/page"

        // FrameFragment
        const val GANK_FRAGMENT = "$MODULE_GANK/fragment"
    }


    // 电影模块
    object Film {
        private const val MODULE_FILM = "/module_senior"

        // film页面
        const val FILM_PAGE = "$MODULE_FILM/page"

        // FILMFragment
        const val FILM_FRAGMENT = "$MODULE_FILM/fragment"
    }

}