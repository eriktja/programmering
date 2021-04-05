package no.hiof.eriktja.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UniverseJSONRepository implements UniverseRepository, Runnable{
    private String fileName;
    private HashMap<String, PlanetSystem> planetSystemHashMap;
    private PlanetSystem[] planetSystemArray;


    // Constructor with filename and readFromFile-method.
    // The readFromFile-methode uses the fileName instance variable
    public UniverseJSONRepository(String fileName) {
        this.fileName = fileName;
        readFromFile(this.fileName);
    }

    // ReadFromFile. Make an instance of ObjectMapper.
    // Read from file to an Array.
    // Loop the array and save the information to the planetSystemHashMap.
    // planetSystemHashMap is not returned because it's an instance variable of the class,
    // so it's basically a "set"-method
    private void readFromFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            planetSystemArray = objectMapper.readValue(new File(fileName), PlanetSystem[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // New planetSystemHashMap
        planetSystemHashMap = new HashMap<>();
        // for-loop putting each element of the planetSystemArray into the planetSystemHashMap.
        // Key is planetSystemName
        for (PlanetSystem planetSystem : planetSystemArray)
            planetSystemHashMap.put(planetSystem.getName(), planetSystem);
    }

    // Make instance of ObjectMapper
    // Write to file from an ArrayList
    public static void writeToFile(ArrayList<PlanetSystem> planetSystems, String fileName){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName) ,planetSystems);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public ArrayList<PlanetSystem> getPlanetSystems() {
        return new ArrayList<>(this.planetSystemHashMap.values());
    }

    @Override
    public PlanetSystem getSpecificPlanetSystem(String name) {
        return this.planetSystemHashMap.get(name);
    }

    @Override
    public ArrayList<Planet> getAllPlanetsInSystem(String name) {
        return new ArrayList<Planet>(this.planetSystemHashMap.get(name).getPlanets());
    }

    @Override
    public Planet getSpecificPlanet(String planetSystemName, String planetName) {
        return getSpecificPlanetSystem(planetSystemName).findPlanet(planetName);
    }

    @Override
    public ArrayList<Planet> deletePlanet(String planetName, String planetSystemName) {
        PlanetSystem planetSystem = this.planetSystemHashMap.get(planetSystemName);
        ArrayList<Planet> planets = planetSystem.getPlanets();
        Iterator<Planet> planetIterator = planets.iterator();
        while (planetIterator.hasNext()){
            Planet aPlanet = planetIterator.next();
            if (aPlanet.getName().equalsIgnoreCase(planetName)){
                planetIterator.remove();
                planetSystem.setPlanets(planets);
                planetSystemHashMap.put(planetSystem.getName(), planetSystem);
                ArrayList<PlanetSystem> updatedPlanetSystem = new ArrayList<>(planetSystemHashMap.values());
                writeToFile(updatedPlanetSystem, "testJSONfile.json");
                return planets;
            }
        }
        return planets;
    }
    @Override
    public ArrayList<Planet> updatePlanet(String planetName ,
                                          String planetSystemName,
                                          HashMap<String, String> planetInfoHashMap) {
        PlanetSystem planetSystem = this.planetSystemHashMap.get(planetSystemName);
        ArrayList<Planet> planets = planetSystem.getPlanets();
        for (Planet aPlanet : planets) {
            if (aPlanet.getName().equalsIgnoreCase(planetName)) {
                aPlanet.setRadius(Double.parseDouble(planetInfoHashMap.get("radius")));
                aPlanet.setMass(Double.parseDouble(planetInfoHashMap.get("mass")));
                aPlanet.setSemiMajorAxis(Double.parseDouble(planetInfoHashMap.get("semiMajorAxis")));
                aPlanet.setEccentricity(Double.parseDouble(planetInfoHashMap.get("eccentricity")));
                aPlanet.setOrbitalPeriod(Double.parseDouble(planetInfoHashMap.get("orbitalPeriod")));
                aPlanet.setPictureUrl(planetInfoHashMap.get("pictureUrl"));
                planetSystemHashMap.put(planetSystem.getName(), planetSystem);
                ArrayList<PlanetSystem> updatedPlanetSystem = new ArrayList<>(planetSystemHashMap.values());
                writeToFile(updatedPlanetSystem, "testJSONfile.json");
                return planets;
            }
        }
        return null;
    }
    /*@Override
    public ArrayList<Planet> updatePlanet(String planetName ,
                                          String planetSystemName,
                                          String name,
                                          double radius,
                                          double mass,
                                          double semiMajorAxis,
                                          double eccentricity,
                                          double orbitalPeriod,
                                          String pictureUrl) {
        PlanetSystem planetSystem = this.planetSystemHashMap.get(planetSystemName);
        ArrayList<Planet> planets = planetSystem.getPlanets();
        for (Planet aPlanet : planets) {
            if (aPlanet.getName().equalsIgnoreCase(planetName)) {
                aPlanet.setName(name);
                aPlanet.setRadius(radius);
                aPlanet.setSemiMajorAxis(semiMajorAxis);
                aPlanet.setEccentricity(eccentricity);
                aPlanet.setOrbitalPeriod(orbitalPeriod);
                aPlanet.setPictureUrl(pictureUrl);
                planetSystemHashMap.put(planetSystem.getName(), planetSystem);
                ArrayList<PlanetSystem> updatedPlanetSystem = new ArrayList<>(planetSystemHashMap.values());
                writeToFile(updatedPlanetSystem, "testJSONfile.json");
                return planets;
            }
        }
        return null;
    }*/

    @Override
    public ArrayList<Planet> createPlanet(Planet planet, String planetSystemName) {
        PlanetSystem planetSystem = this.planetSystemHashMap.get(planetSystemName);
        planet.setCentralCelestialBody(planetSystem.getCenterStar());
        planetSystem.addPlanet(planet);
        this.planetSystemHashMap.put(planetSystemName, planetSystem);
        ArrayList<PlanetSystem> updatedPlanetSystem = new ArrayList<>(planetSystemHashMap.values());
        writeToFile(updatedPlanetSystem, "testJSONfile.json");
        return planetSystem.getPlanets();
    }

    @Override
    public void run() {
        /*public static void writeToFile(ArrayList<PlanetSystem> planetSystems, String fileName){
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName) ,planetSystems);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/
    }
}
