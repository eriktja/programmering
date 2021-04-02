package no.hiof.eriktja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Planet extends NaturalSatellite{

    private static final double JUPITER_RADIUS_IN_KM = 71492;
    private static final double JUPITER_MASS_IN_KG = 1.898E27;
    private static final double EARTH_MASS_IN_KG = 5.972E24;
    private static final double EARTH_RADIUS_IN_KM = 6371;

    public Planet() {
    }

    public Planet(
            String name,
            double radius,
            double mass,
            double semiMajorAxis,
            double eccentricity,
            double orbitalPeriod,
            CelestialBody centralCelestialBody,
            String pictureUrl) {
        super(name, radius, mass, semiMajorAxis, eccentricity, orbitalPeriod, centralCelestialBody, pictureUrl);
    }

    // Calculate a planets actual radius in kilometer
    @Override @JsonIgnore
    public double getRadiusInKm() {
        return getRadius() * JUPITER_RADIUS_IN_KM;
    }
    // Calculate a planets actual mass in kg
    @Override @JsonIgnore
    public double getMassInKg() {
        return getMass() * JUPITER_MASS_IN_KG;
    }
    // Added two variables for planet-detail.vue



    // Calculate a planets mass relative to Earth-standard
    @JsonIgnore
    public double getMEarth() {
        double earthMass = EARTH_MASS_IN_KG;
        return getMassInKg() / earthMass;
    }
    // Calculate a planets radius relative to Earth-standard
    @JsonIgnore
    public double getREarth() {
        double earthRadius = EARTH_RADIUS_IN_KM;
        return this.getRadiusInKm() / earthRadius;
    }

    @Override
    public String toString() {
        return getName() + " has a radius of " + this.getRadiusInKm() + " km " +
                "and a mass of " + getMassInKg() + "kg";
    }

}
