package com.hjc.library_common.router.path

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: Film模块路由
 */
object RouteFilmPath {

    private const val MODULE_FILM = "/module_senior"


    // Activity
    object Activity{

        // FilmActivity
        const val URL_FILM_ACTIVITY = "$MODULE_FILM/activity/film"

    }


    // Fragment
    object Fragment{

        // FilmFragment
        const val URL_FILM_FRAGMENT = "$MODULE_FILM/fragment/film"

    }

}