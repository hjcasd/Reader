package com.hjc.reader.model.response;

import java.util.List;

public class WanCollectBean {

    /**
     * data : {"curPage":1,"datas":[{"author":"鸿洋","chapterId":60,"chapterName":"Android Studio相关","courseId":13,"desc":"","envelopePic":"","id":51412,"link":"http://www.wanandroid.com/blog/show/2030","niceDate":"刚刚","origin":"","originId":8004,"publishTime":1552292177000,"title":"赞助本站...又到了服务器续费期鸟...","userId":16639,"visible":0,"zan":0},{"author":"LinYaoTian","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"计算机网络的课程设计，一个基于局域网的 Android P2P 聊天系统，实现了发送文字、语音、图片、文件等消息。","envelopePic":"http://www.wanandroid.com/blogimgs/ed5ac5b9-9011-4d79-a925-392f0aa9db60.png","id":51411,"link":"http://www.wanandroid.com/blog/show/2513","niceDate":"刚刚","origin":"","originId":8024,"publishTime":1552292161000,"title":" 一个基于局域网的 P2P 聊天应用 - P2P Chat","userId":16639,"visible":0,"zan":0},{"author":"yangchong211","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"组件化综合案例，包含的模块：wanAndroid【kotlin】+干货集中营+知乎日报+番茄Todo+微信精选新闻+豆瓣音乐电影小说+小说读书+简易记事本+搞笑视频+经典游戏+其他更多等等","envelopePic":"http://www.wanandroid.com/blogimgs/92f34dfb-a3ae-44fe-ad1a-d1ff22c773e5.png","id":51410,"link":"http://www.wanandroid.com/blog/show/2517","niceDate":"1分钟前","origin":"","originId":8028,"publishTime":1552292138000,"title":"组件化综合训练案例","userId":16639,"visible":0,"zan":0},{"author":"susion哒哒","chapterId":433,"chapterName":"Window","courseId":13,"desc":"","envelopePic":"","id":50739,"link":"https://www.jianshu.com/p/396d83873ae1","niceDate":"2天前","origin":"","originId":8003,"publishTime":1552033227000,"title":"深入理解Window","userId":16639,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":4}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"鸿洋","chapterId":60,"chapterName":"Android Studio相关","courseId":13,"desc":"","envelopePic":"","id":51412,"link":"http://www.wanandroid.com/blog/show/2030","niceDate":"刚刚","origin":"","originId":8004,"publishTime":1552292177000,"title":"赞助本站...又到了服务器续费期鸟...","userId":16639,"visible":0,"zan":0},{"author":"LinYaoTian","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"计算机网络的课程设计，一个基于局域网的 Android P2P 聊天系统，实现了发送文字、语音、图片、文件等消息。","envelopePic":"http://www.wanandroid.com/blogimgs/ed5ac5b9-9011-4d79-a925-392f0aa9db60.png","id":51411,"link":"http://www.wanandroid.com/blog/show/2513","niceDate":"刚刚","origin":"","originId":8024,"publishTime":1552292161000,"title":" 一个基于局域网的 P2P 聊天应用 - P2P Chat","userId":16639,"visible":0,"zan":0},{"author":"yangchong211","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"组件化综合案例，包含的模块：wanAndroid【kotlin】+干货集中营+知乎日报+番茄Todo+微信精选新闻+豆瓣音乐电影小说+小说读书+简易记事本+搞笑视频+经典游戏+其他更多等等","envelopePic":"http://www.wanandroid.com/blogimgs/92f34dfb-a3ae-44fe-ad1a-d1ff22c773e5.png","id":51410,"link":"http://www.wanandroid.com/blog/show/2517","niceDate":"1分钟前","origin":"","originId":8028,"publishTime":1552292138000,"title":"组件化综合训练案例","userId":16639,"visible":0,"zan":0},{"author":"susion哒哒","chapterId":433,"chapterName":"Window","courseId":13,"desc":"","envelopePic":"","id":50739,"link":"https://www.jianshu.com/p/396d83873ae1","niceDate":"2天前","origin":"","originId":8003,"publishTime":1552033227000,"title":"深入理解Window","userId":16639,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 4
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * author : 鸿洋
             * chapterId : 60
             * chapterName : Android Studio相关
             * courseId : 13
             * desc :
             * envelopePic :
             * id : 51412
             * link : http://www.wanandroid.com/blog/show/2030
             * niceDate : 刚刚
             * origin :
             * originId : 8004
             * publishTime : 1552292177000
             * title : 赞助本站...又到了服务器续费期鸟...
             * userId : 16639
             * visible : 0
             * zan : 0
             */

            private String author;
            private int chapterId;
            private String chapterName;
            private int courseId;
            private String desc;
            private String envelopePic;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private int originId;
            private long publishTime;
            private String title;
            private int userId;
            private int visible;
            private int zan;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
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

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getOriginId() {
                return originId;
            }

            public void setOriginId(int originId) {
                this.originId = originId;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }
        }
    }
}
