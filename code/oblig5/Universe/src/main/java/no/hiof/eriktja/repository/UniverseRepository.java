package no.hiof.eriktja.repository;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;

import java.util.ArrayList;

public interface UniverseRepository {

    ArrayList<PlanetSystem> getPlanetSystems();
    PlanetSystem getSpecificPlanetSystem(String name);
    ArrayList<Planet> getAllPlanetsInSystem(String name);
    Planet getSpecificPlanet(String planetSystemName, String planetName);

    // Task 2.3 Create, update, delete
    ArrayList<Planet> deletePlanet(String planetName, String planetSystemName);
    Planet updatePlanet(Planet planet, String planetSystemName);
    Planet createPlanet(Planet planet, String planetSystemName);
}
