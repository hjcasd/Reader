package com.hjc.reader.model.response;

import java.io.Serializable;
import java.util.List;

public class DBBookBean implements Serializable {

    private int count;
    private int start;
    private int total;
    private List<BooksBean> books;

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

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean implements Serializable {
        /**
         * rating : {"max":10,"numRaters":192,"average":"7.4","min":0}
         * subtitle :
         * author : ["[英] 莫欣·哈米德"]
         * pubdate : 2018-1
         * tags : [{"count":65,"name":"小说","title":"小说"},{"count":48,"name":"外国文学","title":"外国文学"},{"count":42,"name":"外国小说","title":"外国小说"},{"count":41,"name":"莫欣·哈米德","title":"莫欣·哈米德"},{"count":27,"name":"英国","title":"英国"},{"count":25,"name":"好书，值得一读","title":"好书，值得一读"},{"count":20,"name":"英文小说","title":"英文小说"},{"count":20,"name":"纯文学","title":"纯文学"}]
         * origin_title : How to Get Filthy Rich in Rising Asia
         * image : https://img3.doubanio.com/view/subject/m/public/s29658273.jpg
         * binding : 平装
         * translator : ["张雅楠"]
         * catalog : 目 录
         1 搬到大城市／001
         2 接受教育／015
         3 不要堕入爱河／033
         4 远离理想主义者／055
         5 向大师学习／075
         6 为你自己工作／097
         7 做好使用暴力的准备／119
         8 和当官的做朋友／137
         9 保护战争艺术家／155
         10 与债共舞／171
         11 聚焦根本／191
         12 准备好退出策略／207
         * pages : 224
         * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s29658273.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s29658273.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s29658273.jpg"}
         * alt : https://book.douban.com/subject/27199023/
         * id : 27199023
         * publisher : 文汇出版社
         * isbn10 : 7549623864
         * isbn13 : 9787549623860
         * title : 穿过欲望之城
         * url : https://api.douban.com/v2/book/27199023
         * alt_title : How to Get Filthy Rich in Rising Asia
         * author_intro : 莫欣•哈米德
         莫欣•哈米德，文坛鬼才，曾多次入围布克奖。他的代表作累计销量过百万，并被《卫报》选入“定义了十年来的世界”书单。
         1993年，莫欣•哈米德以最高荣誉学位毕业于普林斯顿大学，随后于1997年毕业于哈佛法学院。大学期间，莫欣•哈米德曾向著名作家、诺贝尔文学奖得主托妮•莫里森学习写作。
         莫欣•哈米德《穿过欲望之城》是莫欣•哈米德的代表作之一，一经出版就受到了西方各大媒体的赞誉。美国国家公共电台称它是“国际版的《了不起的盖茨比》”。《纽约时报》也因为《穿过欲望之城》称赞莫欣•哈米德是“这个世代举足轻重的小说家”。
         * summary : 我奔走一生追求金钱，最后才发现我真正渴望的是爱。
         ——————————
         在一个偏僻的村庄里，有一个从没见过滑板车、巧克力或是运动鞋的男孩。一天，他的父母决定举家搬到城市里居住，男孩的命运也随之迎来巨大的转折。
         就如同所有大城市一样，男孩的新家是一个蕴含着无限可能的地方。他暗暗下定决心，自己人生的目标就是成为有钱人。为了这个目标，他可以付出一切代价。
         男孩把握住了每一个机会，命运也眷顾了他。然而随着年龄的增长，他渐渐意识到了自己真正渴望什么……
         ——————————
         ◆《穿过欲望之城》被美国国家公共电台评为“国际版的《了不起的盖茨比》”！
         ◆《穿过欲望之城》接连被15家世界主流媒体评为年度图书！
         ◆《穿过欲望之城》让各国读者争相推荐这部令人笑中带泪的小说！
         ◆《穿过欲望之城》是文坛鬼才莫欣·哈米德的重要代表作！
         ——————————
         令人眩晕。令人上瘾。莫欣·哈米德运用他高超的写作能力，讲述了一个美妙的故事。一本无与伦比的小说：柔软、尖锐又大胆。——《卫报》
         ◆
         全书只有十二个章节，干脆利落，却是一场了不起的旅程。——《经济学人》
         ◆
         《穿过欲望之城》的感情如此丰富，对生命意义的讨论也直击要害。——《华盛顿独立书评》
         ◆
         莫欣·哈米德无疑满足了读者的期待。《穿过欲望之城》不可思议又感人至深。——《时代周刊》
         ◆
         简直无与伦比。——《每日邮报》
         ◆
         这部小说体现出了作者的野心。精妙又丰富。 ——《出版人周刊》
         * price : 39.90
         * series : {"id":"6163","title":"珍妮特·温特森系列"}
         * ebook_url : https://read.douban.com/ebook/33094946/
         * ebook_price : 9.99
         */

        private RatingBean rating;
        private String subtitle;
        private String pubdate;
        private String origin_title;
        private String image;
        private String binding;
        private String catalog;
        private String pages;
        private ImagesBean images;
        private String alt;
        private String id;
        private String publisher;
        private String isbn10;
        private String isbn13;
        private String title;
        private String url;
        private String alt_title;
        private String author_intro;
        private String summary;
        private String price;
        private SeriesBean series;
        private String ebook_url;
        private String ebook_price;
        private List<String> author;
        private List<TagsBean> tags;
        private List<String> translator;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getOrigin_title() {
            return origin_title;
        }

        public void setOrigin_title(String origin_title) {
            this.origin_title = origin_title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
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

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn10() {
            return isbn10;
        }

        public void setIsbn10(String isbn10) {
            this.isbn10 = isbn10;
        }

        public String getIsbn13() {
            return isbn13;
        }

        public void setIsbn13(String isbn13) {
            this.isbn13 = isbn13;
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

        public String getAlt_title() {
            return alt_title;
        }

        public void setAlt_title(String alt_title) {
            this.alt_title = alt_title;
        }

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public SeriesBean getSeries() {
            return series;
        }

        public void setSeries(SeriesBean series) {
            this.series = series;
        }

        public String getEbook_url() {
            return ebook_url;
        }

        public void setEbook_url(String ebook_url) {
            this.ebook_url = ebook_url;
        }

        public String getEbook_price() {
            return ebook_price;
        }

        public void setEbook_price(String ebook_price) {
            this.ebook_price = ebook_price;
        }

        public List<String> getAuthor() {
            return author;
        }

        public void setAuthor(List<String> author) {
            this.author = author;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<String> getTranslator() {
            return translator;
        }

        public void setTranslator(List<String> translator) {
            this.translator = translator;
        }

        public static class RatingBean implements Serializable {
            /**
             * max : 10
             * numRaters : 192
             * average : 7.4
             * min : 0
             */

            private int max;
            private int numRaters;
            private String average;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getNumRaters() {
                return numRaters;
            }

            public void setNumRaters(int numRaters) {
                this.numRaters = numRaters;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean implements Serializable  {
            /**
             * small : https://img3.doubanio.com/view/subject/s/public/s29658273.jpg
             * large : https://img3.doubanio.com/view/subject/l/public/s29658273.jpg
             * medium : https://img3.doubanio.com/view/subject/m/public/s29658273.jpg
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

        public static class SeriesBean implements Serializable {
            /**
             * id : 6163
             * title : 珍妮特·温特森系列
             */

            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class TagsBean implements Serializable {
            /**
             * count : 65
             * name : 小说
             * title : 小说
             */

            private int count;
            private String name;
            private String title;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
