package com.hjc.reader.model.response;

import java.util.List;

public class DBMovieDetailBean {

    /**
     * rating : {"max":10,"average":8.9,"stars":"45","min":0}
     * reviews_count : 2200
     * wish_count : 365133
     * douban_site :
     * year : 2018
     * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg"}
     * alt : https://movie.douban.com/subject/27060077/
     * id : 27060077
     * mobile_url : https://movie.douban.com/subject/27060077/mobile
     * title : 绿皮书
     * do_count : null
     * share_url : https://m.douban.com/movie/subject/27060077
     * seasons_count : null
     * schedule_url : https://movie.douban.com/subject/27060077/cinema/
     * episodes_count : null
     * countries : ["美国"]
     * genres : ["剧情","喜剧","传记"]
     * collect_count : 337023
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054520/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg"},"name":"维果·莫腾森","id":"1054520"},{"alt":"https://movie.douban.com/celebrity/1004702/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488440651.5.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488440651.5.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488440651.5.jpg"},"name":"马赫沙拉·阿里","id":"1004702"},{"alt":"https://movie.douban.com/celebrity/1010545/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1405786644.35.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1405786644.35.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1405786644.35.jpg"},"name":"琳达·卡德里尼","id":"1010545"},{"alt":"https://movie.douban.com/celebrity/1379169/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1502701147.13.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1502701147.13.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1502701147.13.jpg"},"name":"塞巴斯蒂安·马尼斯科","id":"1379169"}]
     * current_season : null
     * original_title : Green Book
     * summary : 一名黑人钢琴家，为前往种族歧视严重的南方巡演，找了一个粗暴的白人混混做司机。在一路开车南下的过程里，截然不同的两人矛盾不断，引发了不少争吵和笑料。但又在彼此最需要的时候，一起共渡难关。行程临近结束，两人也慢慢放下了偏见......
     绿皮书，是一本专为黑人而设的旅行指南，标注了各城市中允许黑人进入的旅店、餐馆。电影由真实故事改编。
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1036662/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg"},"name":"彼得·法雷里","id":"1036662"}]
     * comments_count : 93308
     * ratings_count : 269532
     * aka : ["绿簿旅友(港)","幸福绿皮书(台)","绿书"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    private int collect_count;
    private Object current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<String> countries;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 8.9
         * stars : 45
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg
         * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg
         * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1054520/
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg"}
         * name : 维果·莫腾森
         * id : 1054520
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg
             * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg
             * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29922.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1036662/
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg"}
         * name : 彼得·法雷里
         * id : 1036662
         */

        private String alt;
        private AvatarsBeanX avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg
             * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg
             * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29610.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }
}
