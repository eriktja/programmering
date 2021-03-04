package no.hiof.eriktja.model;

import org.jetbrains.annotations.NotNull;

public class Planet extends NaturalSatellite implements Comparable<CelestialBody>{

    private static final double JUPITER_RADIUS_IN_KM = 71492;
    private static final double JUPITER_MASS_IN_KG = 1.898E27;
    private static final double EARTH_MASS_IN_KG = 5.972E24;
    private static final double EARTH_RADIUS_IN_KM = 6371;

    public Planet(String name, double radius, double mass, double semiMajorAxis, double eccentricity, double orbitalPeriod, CelestialBody centralCelestialBody) {
        super(name, radius, mass, semiMajorAxis, eccentricity, orbitalPeriod, centralCelestialBody);
    }

    // Calculate a planets actual radius in kilometer
    @Override
    public double getKmRadius() {
        return getRadius() * JUPITER_RADIUS_IN_KM;
    }

    // Calculate a planets actual mass in kg
    @Override
    public double getKgMass() {
        return getMass() * JUPITER_MASS_IN_KG;
    }


    // Calculate a planets mass relative to Earth-standard
    public double getMEarth() {
        double earthMass = EARTH_MASS_IN_KG;
        return getKgMass() / earthMass;
    }
    // Calculate a planets radius relative to Earth-standard
    public double getREarth() {
        double earthRadius = EARTH_RADIUS_IN_KM;
        return getKmRadius() / earthRadius;
    }

    @Override
    public String toString() {
        return getName() + " has a radius of " + getKmRadius() + " km " +
                "and a mass of " + getKgMass() + "kg";
    }

    @Override
    public int compareTo(@NotNull CelestialBody otherPlanet) {
        if (getRadius() == otherPlanet.getRadius())
            return 0;
        else if (getRadius() < otherPlanet.getRadius())
            return -1;
        return 1;
    }
}
