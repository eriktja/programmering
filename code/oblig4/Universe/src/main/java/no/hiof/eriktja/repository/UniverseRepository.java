package no.hiof.eriktja.repository;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;

import java.util.ArrayList;

public interface UniverseRepository {

    ArrayList<PlanetSystem> getPlanetSystems();
    PlanetSystem getSpecificPlanetSystem(String name);
    ArrayList<Planet> getAllPlanetsInSystem(String name);
    Planet getSpecificPlanet(String planetSystemName, String planetName);
}
