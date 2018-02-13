package com.example.kevin.meteo.models;

import java.util.List;

/**
 * Created by kevin on 13/02/2018.
 */

public class OpenWeatherMap {

    private String name;
    private String message;
    private String cod;
    private OWMain main;
    private List<OWWeather> weather;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public OWMain getMain() {
        return main;
    }

    public void setMain(OWMain main) {
        this.main = main;
    }

    public List<OWWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<OWWeather> weather) {
        this.weather = weather;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
