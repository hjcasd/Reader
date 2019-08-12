package com.hjc.reader.model.response;

import java.util.List;

public class MovieHotBean {
    private int totalComingMovie;
    private List<MovieItemBean> ms;

    public int getTotalComingMovie() {
        return totalComingMovie;
    }

    public void setTotalComingMovie(int totalComingMovie) {
        this.totalComingMovie = totalComingMovie;
    }

    public List<MovieItemBean> getMs() {
        return ms;
    }

    public void setMs(List<MovieItemBean> ms) {
        this.ms = ms;
    }
}
