package com.hjc.reader.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class History {
    @Id(autoincrement = true)
    private Long id;
    private String name;

    @Generated(hash = 1612502674)
    public History(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 869423138)
    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
