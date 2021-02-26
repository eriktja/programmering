package no.hiof.eriktja.model;

import java.util.ArrayList;

public class PlanetSystem {
    private String systemName;
    private Star centerStar;
    private ArrayList<Planet> planets = new ArrayList<>();

    public PlanetSystem(){}

    public PlanetSystem(String systemName, Star centerStar){
        this.systemName = systemName;
        this.centerStar = centerStar;
    }

    public void setSystemName(String systemName){
        this.systemName = systemName;
    }
    public String getSystemName(){
        return systemName;
    }

    public void setCenterStar(Star centerStar){
        this.centerStar = centerStar;
    }
    public Star getCenterStar(){
        return centerStar;
    }

    public ArrayList getPlanets() {
        return new ArrayList<Planet>(planets);
    }

    public void setPlanets(Planet planet) {
        planets.add(planet);
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
        return "The "+ systemName +
                " has the " + centerStar.getName() +
                " as the center star. It has " + planets.size() + " planets.";
    }
}
