package com.hjc.reader.base.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @Author: HJC
 * @Date: 2019/1/4 14:57
 * @Description: EventBus管理类
 */
public class EventManager {

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void sendEvent(MessageEvent messageEvent) {
        EventBus.getDefault().post(messageEvent);
    }

    public static void sendStickyEvent(MessageEvent messageEvent) {
        EventBus.getDefault().postSticky(messageEvent);
    }

}
