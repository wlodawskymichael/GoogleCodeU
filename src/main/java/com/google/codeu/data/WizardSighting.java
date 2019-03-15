package com.google.codeu.data;

public class WizardSighting {
    private String state;
    private double lat;
    private double lng;

    public WizardSighting(String state, double lat, double lng) {
        this.state = state;
        this.lat = lat;
        this.lng = lng;
    }

    public String getState() {
        return state;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}