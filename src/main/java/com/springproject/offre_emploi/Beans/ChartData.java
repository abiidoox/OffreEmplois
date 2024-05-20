package com.springproject.offre_emploi.Beans;

import java.util.List;

public class ChartData {
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<String> labels;
    private List<Integer> data;

    public ChartData(List<String> labels, List<Integer> data) {
        this.labels = labels;
        this.data = data;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
// Constructors, getters, and setters
}
