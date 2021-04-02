package no.hiof.eriktja.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UniverseJSONRepository implements UniverseRepository{
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
        ArrayList<PlanetSystem> planetSystems = getPlanetSystems();
        ArrayList<Planet> planets = new ArrayList<>();
        for (PlanetSystem planetSystem : planetSystems) {
            if (planetSystem.getName().equalsIgnoreCase(planetSystemName))
                planets = planetSystem.getPlanets();
                planets.removeIf(planet -> planet.getName().equalsIgnoreCase(planetName));
            writeToFile(planetSystems, "testJSONfile.json");
            return planets;
        }
        return null;
    }

    @Override
    public Planet updatePlanet(Planet planet, String planetSystemName) {
        return null;
    }

    @Override
    public Planet createPlanet(Planet planet, String planetSystemName) {

        return planet;
    }
}
