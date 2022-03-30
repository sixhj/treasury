package com.treasury.keyby6;

public class Menu {

    private Integer id;
    private String title;
    private Integer sort;

    public Menu(Integer id, String title, Integer sort) {
        this.id = id;
        this.title = title;
        this.sort = sort;
    }

    public Menu() {
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
