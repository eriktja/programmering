package no.hiof.eriktja.controller;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;
import no.hiof.eriktja.repository.UniverseDataRepository;
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

    public void getAllPlanetsInSystem(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        ArrayList<Planet> planets = universeRepository.getAllPlanetsInSystem(planetSystemName);
        String sort_by = ctx.queryParam("sort_by");
        if (sort_by.equals("num"))
             ctx.json(universeRepository.getAllPlanetsInSystem(planetSystemName));
        if (sort_by.equals("radius"))
            Collections.sort(planets);
        if (!sort_by.equals("radius")){
            Collections.sort(planets, new Comparator<Planet>() {
                @Override
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
