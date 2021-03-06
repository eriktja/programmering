package no.hiof.eriktja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class NaturalSatellite extends CelestialBody{
    private double semiMajorAxis;
    private double eccentricity;
    private double orbitalPeriod;
    private CelestialBody centralCelestialBody;
    private static final double ASTRONOMICAL_UNITS = 149597871;
    private static final double GRAVITATIONAL_CONSTANT = 6.67408E-11;
    private static final double KILO = 1000;

    public NaturalSatellite() {
    }

    public NaturalSatellite(
            String name,
            double radius,
            double mass,
            double semiMajorAxis,
            double eccentricity,
            double orbitalPeriod,
            CelestialBody centralCelestialBody,
            String pictureUrl) {
        super(name, radius, mass, pictureUrl);
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.orbitalPeriod = orbitalPeriod;
        this.centralCelestialBody = centralCelestialBody;
    }

    public double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public CelestialBody getCentralCelestialBody() {
        return centralCelestialBody;
    }

    public void setCentralCelestialBody(CelestialBody centralCelestialBody) {
        this.centralCelestialBody = centralCelestialBody;
    }

    // r = a(1 - e²) / 1 + e * cos
    // Calculate distance to celestial body by radian. Answer in Astronomical units.
    @JsonIgnore
    public double distanceToCentralBodyRadian(double radian){
        return (getSemiMajorAxis() * (1 - Math.pow(getEccentricity(), 2))) /
                (1 + (eccentricity * Math.cos(radian)));
    }

    // Calculate distance to celestial body by degree. Answer in Astronomical units.
    @JsonIgnore
    public double distanceToCentralBodyDegrees(double degrees){
        return distanceToCentralBodyRadian(Math.toRadians(degrees));
    }

    // Convert from Astronomical units to kilometer
    @JsonIgnore
    public static double distanceFromAuToKM(double distance){
        return distance * ASTRONOMICAL_UNITS;
    }
    @JsonIgnore
    public double getDistanceToCentralBodyKm(double degrees) {
        return distanceFromAuToKM(distanceToCentralBodyDegrees(degrees));
    }

    // v = sqrt(G*M / r)
    // Get orbital velocity in meters/second. Input distance in kilometer
    @JsonIgnore
    public double orbitingVelocityMS(double distance) {
        return Math.sqrt((GRAVITATIONAL_CONSTANT * getCentralCelestialBody().getMassInKg()) / (distance * KILO));
    }
    // Get orbital velocity in kilometers/second
    @JsonIgnore
    public double orbitingVelocityKMS(double distance) {
        return orbitingVelocityMS(distance) / KILO;
    }

    // Combines the "getDistanceToCentralBody"-method and "orbitingVelocityKMS"-method,
    // in order to make it easier to find the Velocity
    @JsonIgnore
    public double getOrbitalVelocityKm(double degrees){
         return orbitingVelocityKMS(distanceFromAuToKM(distanceToCentralBodyDegrees(degrees)));
    }

    // Number of degrees rotated each orbitalPeriodUnit
    @JsonIgnore
    public double getDegreesFromOrbitalPeriod(){
        return 360 / this.getOrbitalPeriod();
    }

    // Gives the current distance form the central body, given all the plants start in a line at day 0
    @JsonIgnore
    public double getCurrentDistanceFromCentralBody(double days){
        double degrees = this.getDegreesFromOrbitalPeriod() * days;
        return getDistanceToCentralBodyKm(degrees);

    }
    // Get the distance between 2 planets after a given number of days. Given both start at a line on day 0.
    @JsonIgnore
    public double getPlanetRelativeDistance(Planet planet, double days){
        double planet1Distance = this.getCurrentDistanceFromCentralBody(days);
        double planet2Distance = planet.getCurrentDistanceFromCentralBody(days);
        double planet1degrees = this.getDegreesFromOrbitalPeriod() * days;
        double planet2degrees = planet.getDegreesFromOrbitalPeriod() * days;
        double angleBetween = planet1degrees - planet2degrees;
        // a² = b² + c² - 2bc * cosA
        double relativeDistance = (Math.pow(planet1Distance, 2) + Math.pow(planet2Distance, 2))
                - (2 * planet1Distance * planet2Distance * Math.cos(Math.toRadians(angleBetween)));
        // The formula returns a²
        return Math.sqrt(relativeDistance);
    }


    // Make a variable which contains the distance at 0 degrees, and then compare that to the distance at 1-360 degrees.
    @JsonIgnore
    public double getLargestDistanceToCentralBody(){
        double longestDistance = getDistanceToCentralBodyKm(0);
        for (int i = 1;i < getOrbitalPeriod(); i++){
            double distanToCheck = getDistanceToCentralBodyKm(getDegreesFromOrbitalPeriod() * i);
            longestDistance = Math.max(distanToCheck, longestDistance);
        }
        return longestDistance;
    }

    // Make a variable which contains the distance at 0 degrees, and then compare that to the distance at 1-360 degrees.
    @JsonIgnore
    public double getShortestDistanceToCentralBody(){
        double shortestDistance = getDistanceToCentralBodyKm(0);
        for (int i = 1;i < getOrbitalPeriod(); i++){
            double distanToCheck = getDistanceToCentralBodyKm(getDegreesFromOrbitalPeriod() * i);
            shortestDistance = Math.min(distanToCheck, shortestDistance);
        }
        return shortestDistance;
    }


    // Calculate a planets surface gravity
    @JsonIgnore
    public double getSurfaceGravity() {
        // g = GM / R²
        double meter = getRadiusInKm() * 1000;
        return (GRAVITATIONAL_CONSTANT * getMassInKg()) / Math.pow(meter,2);
    }

    @Override @JsonIgnore
    public abstract double getRadiusInKm();
    @Override @JsonIgnore
    public abstract double getMassInKg();
}
