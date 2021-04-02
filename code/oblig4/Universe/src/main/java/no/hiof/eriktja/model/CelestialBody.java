package no.hiof.eriktja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;

public abstract class CelestialBody implements Comparable<CelestialBody> {
    private String name;
    private double radius, mass;
    private String pictureUrl;

    public CelestialBody() {
    }

    public CelestialBody(String name, double radius, double mass, String pictureUrl) {
        this.name = name;
        this.radius = radius;
        this.mass = mass;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }


    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    @JsonIgnore
    public abstract double getKmRadius();
    @JsonIgnore
    public abstract double getKgMass();

    @Override
    public int compareTo(@NotNull CelestialBody anotherBody) {
        int value = (int)(this.getKmRadius() - anotherBody.getKmRadius());
        if (value == 0)
            return (int) (this.getKgMass() - anotherBody.getKgMass());

        return value;
    }
}
