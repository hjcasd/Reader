package com.hjc.reader.bean.response;

import java.io.Serializable;
import java.util.List;

public class MovieComingBean {

    private List<AttentionBean> attention;
    private List<ComingItemBean> moviecomings;

    public List<AttentionBean> getAttention() {
        return attention;
    }

    public void setAttention(List<AttentionBean> attention) {
        this.attention = attention;
    }

    public List<ComingItemBean> getMoviecomings() {
        return moviecomings;
    }

    public void setMoviecomings(List<ComingItemBean> moviecomings) {
        this.moviecomings = moviecomings;
    }

    public static class AttentionBean {
        /**
         * actor1 : 任达华
         * actor2 : 梁咏琪
         * director : 罗永昌
         * id : 261864
         * image : http://img5.mtime.cn/mt/2019/07/02/153545.63409864_1280X720X2.jpg
         * isFilter : false
         * isTicket : false
         * isVideo : true
         * locationName : 中国
         * rDay : 20
         * rMonth : 9
         * rYear : 2019
         * releaseDate : 9月20日上映
         * title : 小Q
         * type : 家庭
         * videoCount : 3
         * videos : [{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/07/16/100711.44220041.jpg","length":142,"title":"小Q 终极预告","url":"http://vfx.mtime.cn/Video/2019/07/16/mp4/190716102033676637.mp4","videoId":75495},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/05/08/101629.44546498.jpg","length":60,"title":"小Q  \"天使降临\"定档预告","url":"http://vfx.mtime.cn/Video/2019/05/08/mp4/190508101714623648.mp4","videoId":74629},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/07/11/113249.31563225.jpg","length":161,"title":"小Q \u201c忠爱告白\u201d版预告","url":"http://vfx.mtime.cn/Video/2019/07/11/mp4/190711113255914537.mp4","videoId":75443}]
         * wantedCount : 231
         */

        private String actor1;
        private String actor2;
        private String director;
        private int id;
        private String image;
        private boolean isFilter;
        private boolean isTicket;
        private boolean isVideo;
        private String locationName;
        private int rDay;
        private int rMonth;
        private int rYear;
        private String releaseDate;
        private String title;
        private String type;
        private int videoCount;
        private int wantedCount;
        private List<VideosBean> videos;

        public String getActor1() {
            return actor1;
        }

        public void setActor1(String actor1) {
            this.actor1 = actor1;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIsFilter() {
            return isFilter;
        }

        public void setIsFilter(boolean isFilter) {
            this.isFilter = isFilter;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int getRDay() {
            return rDay;
        }

        public void setRDay(int rDay) {
            this.rDay = rDay;
        }

        public int getRMonth() {
            return rMonth;
        }

        public void setRMonth(int rMonth) {
            this.rMonth = rMonth;
        }

        public int getRYear() {
            return rYear;
        }

        public void setRYear(int rYear) {
            this.rYear = rYear;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public int getWantedCount() {
            return wantedCount;
        }

        public void setWantedCount(int wantedCount) {
            this.wantedCount = wantedCount;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        public static class VideosBean {
            /**
             * hightUrl :
             * image : http://img5.mtime.cn/mg/2019/07/16/100711.44220041.jpg
             * length : 142
             * title : 小Q 终极预告
             * url : http://vfx.mtime.cn/Video/2019/07/16/mp4/190716102033676637.mp4
             * videoId : 75495
             */

            private String hightUrl;
            private String image;
            private int length;
            private String title;
            private String url;
            private int videoId;

            public String getHightUrl() {
                return hightUrl;
            }

            public void setHightUrl(String hightUrl) {
                this.hightUrl = hightUrl;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }
        }
    }

    public static class ComingItemBean implements Serializable {
        /**
         * actor1 : 张家辉
         * actor2 : 杨紫
         * director : 雷尼·哈林
         * id : 234951
         * image : http://img5.mtime.cn/mt/2019/08/02/154517.87706169_1280X720X2.jpg
         * isFilter : false
         * isTicket : true
         * isVideo : true
         * locationName : 中国
         * rDay : 16
         * rMonth : 8
         * rYear : 2019
         * releaseDate : 8月16日上映
         * title : 沉默的证人
         * type : 动作 / 犯罪
         * videoCount : 3
         * videos : [{"hightUrl":"","image":"http://img5.mtime.cn/mg/2018/12/24/115155.62544767.jpg","length":89,"title":"沉默的证人 创意预告","url":"http://vfx.mtime.cn/Video/2018/12/24/mp4/181224115254353985.mp4","videoId":73121},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2018/03/14/221454.92519141.jpg","length":30,"title":"沉默的证人 先导预告","url":"http://vfx.mtime.cn/Video/2018/03/14/mp4/180314221159305319.mp4","videoId":69903},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/07/08/090411.92687754.jpg","length":94,"title":"沉默的证人 定档预告","url":"http://vfx.mtime.cn/Video/2019/07/08/mp4/190708090501732699.mp4","videoId":75388}]
         * wantedCount : 638
         */

        private String actor1;
        private String actor2;
        private String director;
        private int id;
        private String image;
        private boolean isFilter;
        private boolean isTicket;
        private boolean isVideo;
        private String locationName;
        private int rDay;
        private int rMonth;
        private int rYear;
        private String releaseDate;
        private String title;
        private String type;
        private int videoCount;
        private int wantedCount;
        private List<VideosBeanX> videos;

        public String getActor1() {
            return actor1;
        }

        public void setActor1(String actor1) {
            this.actor1 = actor1;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIsFilter() {
            return isFilter;
        }

        public void setIsFilter(boolean isFilter) {
            this.isFilter = isFilter;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int getRDay() {
            return rDay;
        }

        public void setRDay(int rDay) {
            this.rDay = rDay;
        }

        public int getRMonth() {
            return rMonth;
        }

        public void setRMonth(int rMonth) {
            this.rMonth = rMonth;
        }

        public int getRYear() {
            return rYear;
        }

        public void setRYear(int rYear) {
            this.rYear = rYear;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public int getWantedCount() {
            return wantedCount;
        }

        public void setWantedCount(int wantedCount) {
            this.wantedCount = wantedCount;
        }

        public List<VideosBeanX> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBeanX> videos) {
            this.videos = videos;
        }

        public static class VideosBeanX {
            /**
             * hightUrl :
             * image : http://img5.mtime.cn/mg/2018/12/24/115155.62544767.jpg
             * length : 89
             * title : 沉默的证人 创意预告
             * url : http://vfx.mtime.cn/Video/2018/12/24/mp4/181224115254353985.mp4
             * videoId : 73121
             */

            private String hightUrl;
            private String image;
            private int length;
            private String title;
            private String url;
            private int videoId;

            public String getHightUrl() {
                return hightUrl;
            }

            public void setHightUrl(String hightUrl) {
                this.hightUrl = hightUrl;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }
        }
    }
}
