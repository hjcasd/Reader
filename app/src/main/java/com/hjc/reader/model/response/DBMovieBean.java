package com.hjc.reader.model.response;

import java.io.Serializable;
import java.util.List;

public class DBMovieBean implements Serializable {

    private int count;
    private int start;
    private int total;
    private String title;
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean implements Serializable {
        /**
         * rating : {"max":10,"average":7.5,"stars":"40","min":0}
         * genres : ["喜剧","动作","科幻"]
         * title : 死侍2：我爱我家
         * casts : [{"alt":"https://movie.douban.com/celebrity/1053623/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p48608.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p48608.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p48608.jpg"},"name":"瑞恩·雷诺兹","id":"1053623"},{"alt":"https://movie.douban.com/celebrity/1004568/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1413615170.39.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1413615170.39.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1413615170.39.jpg"},"name":"乔什·布洛林","id":"1004568"},{"alt":"https://movie.douban.com/celebrity/1362593/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/puqS3biE9tVocel_avatar_uploaded1474733946.09.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/puqS3biE9tVocel_avatar_uploaded1474733946.09.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/puqS3biE9tVocel_avatar_uploaded1474733946.09.jpg"},"name":"朱利安·迪尼森","id":"1362593"}]
         * collect_count : 225916
         * original_title : Deadpool 2
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1289765/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416221591.26.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416221591.26.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416221591.26.jpg"},"name":"大卫·雷奇","id":"1289765"}]
         * year : 2018
         * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg"}
         * alt : https://movie.douban.com/subject/26588308/
         * id : 26588308
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<PersonBean> casts;
        private List<PersonBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
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

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<PersonBean> getCasts() {
            return casts;
        }

        public void setCasts(List<PersonBean> casts) {
            this.casts = casts;
        }

        public List<PersonBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<PersonBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean implements Serializable{
            /**
             * max : 10
             * average : 7.5
             * stars : 40
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

        public static class ImagesBean implements Serializable{
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2545479945.jpg
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

        public static class PersonBean implements Serializable{
            private String alt;
            private String type;
            private ImagesBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public ImagesBean getAvatars() {
                return avatars;
            }

            public void setAvatars(ImagesBean avatars) {
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
        }
    }
}
