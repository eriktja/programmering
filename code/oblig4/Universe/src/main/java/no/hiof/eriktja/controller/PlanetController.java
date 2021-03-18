package no.hiof.eriktja.controller;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.repository.UniverseRepository;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlanetController {
    private UniverseRepository universeRepository;

    public PlanetController(UniverseRepository universeRepository) {
        this.universeRepository = universeRepository;
    }

    // methods to display all planets in vue, with sorting.
    public void getAllPlanetsInSystem(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        ArrayList<Planet> planets = universeRepository.getAllPlanetsInSystem(planetSystemName);
        String sort_by = ctx.queryParam("sort_by");
        if (sort_by.equals("num")) //num returns original ArrayList of planets
             ctx.json(universeRepository.getAllPlanetsInSystem(planetSystemName));
        else if (sort_by.equals("radius")) //radius returns the method from task 2.1
            Collections.sort(planets);
        else {
            Collections.sort(planets, new Comparator<Planet>() {
                @Override // create methods for name and mass
                public int compare(Planet aPlanet, Planet otherPlanet) {
                    if (sort_by.equals("name"))
                        return aPlanet.getName().compareTo(otherPlanet.getName());
                    if(sort_by.equals("mass"))
                        return (int) (aPlanet.getKgMass() - otherPlanet.getKgMass());
                    return 0;
                }
            });
        }
        ctx.json(planets);
    }

    public void getSpecificPlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        Planet planet = universeRepository.getSpecificPlanet(planetSystemName,planetName);
        ctx.json(planet);
    }
}
