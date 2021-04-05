package no.hiof.eriktja.repository;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;

import java.util.ArrayList;
import java.util.HashMap;

public interface UniverseRepository {

    ArrayList<PlanetSystem> getPlanetSystems();
    PlanetSystem getSpecificPlanetSystem(String name);
    ArrayList<Planet> getAllPlanetsInSystem(String name);
    Planet getSpecificPlanet(String planetSystemName, String planetName);

    // Task 2.3 Create, update, delete
    ArrayList<Planet> deletePlanet(String planetName, String planetSystemName);
    // planetSystemName, name, radius, mass, semiMajorAxis, eccentricity, orbitalPeriod, pictureUrl
    //ArrayList<Planet> updatePlanet(String planetName, String planetSystemName, String name, double radius, double mass, double semiMajorAxis, double eccentricity, double orbitalPeriod, String pictureUrl);
    ArrayList<Planet> updatePlanet(String planetName, String planetSystemName, HashMap<String, String> planetInfoHashMap);
    ArrayList<Planet> createPlanet(Planet planet, String planetSystemName);


}
