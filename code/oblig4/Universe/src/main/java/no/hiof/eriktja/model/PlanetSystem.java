package no.hiof.eriktja.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlanetSystem implements Comparable<Planet> {
    private String name;
    private Star centerStar;
    private String pictureUrl;
    private ArrayList<Planet> planets = new ArrayList<>();

    public PlanetSystem(String name, Star centerStar, String pictureUrl){
        this.name = name;
        this.centerStar = centerStar;
        this.pictureUrl = pictureUrl;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCenterStar(Star centerStar){
        this.centerStar = centerStar;
    }
    public Star getCenterStar(){
        return centerStar;
    }

    public ArrayList<Planet> getPlanets() {
        return new ArrayList<Planet>(planets);
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    // Return the smallest planet
    public Planet getSmallest() {
        if (planets.size() == 0)
            return null;

        Planet smallestPlanet = planets.get(0);

        for (Planet currentPlanet : planets) {
            if (smallestPlanet.getRadius() > currentPlanet.getRadius())
                smallestPlanet = currentPlanet;
            else if (smallestPlanet.getRadius() == currentPlanet.getRadius()
                && smallestPlanet.getMass() > currentPlanet.getMass())
                    smallestPlanet = currentPlanet;

        }
        return smallestPlanet;
    }

    // Return biggest planet
    public Planet getBiggest() {
        if (planets.size() == 0)
            return null;

        Planet biggestPlanet = planets.get(0);

        for (Planet currentPlanet : planets) {
            if (biggestPlanet.getRadius() < currentPlanet.getRadius())
                biggestPlanet = currentPlanet;
            else if (biggestPlanet.getRadius() == currentPlanet.getRadius()
                 && biggestPlanet.getMass() < currentPlanet.getMass())
                    biggestPlanet = currentPlanet;
        }
        return biggestPlanet;
    }

    public Planet findPlanet(String planetName){
        for (Planet singlePlanet : planets) {
            if (singlePlanet.getName().equalsIgnoreCase(planetName))
                return singlePlanet;
        }
        return null;
    }

    @Override
    public String toString() {
        return "The "+ name +
                " has the " + centerStar.getName() +
                " as the center star. It has " + planets.size() + " planets.";
    }

    @Override
    public int compareTo(@NotNull Planet o) {
        return 0;
    }
}
