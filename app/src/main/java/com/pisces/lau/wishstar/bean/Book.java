package com.pisces.lau.wishstar.bean;

/**
 * Created by Liu Wenyue on 2015/7/23.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */

public class Book {
    /**
     * id : 6515839
     * title : 疯狂Android讲义
     */
    private String id;
    private String title;
    private ImagesEntity images;

    public ImagesEntity getImages() {
        return images;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static class ImagesEntity {
        /**
         * small : http://img1.douban.com/spic/s1001902.jpg
         * large : http://img1.douban.com/lpic/s1001902.jpg
         * medium : http://img1.douban.com/mpic/s1001902.jpg
         */

        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }

}

