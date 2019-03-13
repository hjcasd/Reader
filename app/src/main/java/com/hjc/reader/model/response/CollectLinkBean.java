package com.hjc.reader.model.response;

import java.io.Serializable;
import java.util.List;

public class CollectLinkBean {

    /**
     * data : [{"desc":"","icon":"","id":1218,"link":"https://juejin.im/post/5bb9c0d55188255c7566e1e2","name":"回归初心：极简 Android 组件化方案 &mdash; AppJoint - 掘金","order":0,"userId":16639,"visible":1},{"desc":"","icon":"","id":1219,"link":"https://github.com/myliang/x-spreadsheet","name":"GitHub - myliang/x-spreadsheet: A web-based JavaScript（canvas） spreadsheet","order":0,"userId":16639,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * desc :
         * icon :
         * id : 1218
         * link : https://juejin.im/post/5bb9c0d55188255c7566e1e2
         * name : 回归初心：极简 Android 组件化方案 &mdash; AppJoint - 掘金
         * order : 0
         * userId : 16639
         * visible : 1
         */

        private String desc;
        private String icon;
        private int id;
        private String link;
        private String name;
        private int order;
        private int userId;
        private int visible;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }
    }
}
