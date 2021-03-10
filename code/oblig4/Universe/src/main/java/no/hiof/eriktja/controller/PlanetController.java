package no.hiof.eriktja.controller;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;
import no.hiof.eriktja.repository.UniverseDataRepository;
import no.hiof.eriktja.repository.UniverseRepository;
import io.javalin.http.Context;

import java.util.ArrayList;

public class PlanetController {
    private UniverseRepository universeRepository;

    public PlanetController(UniverseRepository universeRepository) {
        this.universeRepository = universeRepository;
    }

    public void getAllPlanetsInSystem(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        ctx.json(universeRepository.getAllPlanetsInSystem(planetSystemName));
    }


    public void getSpecificPlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        Planet planet = universeRepository.getSpecificPlanet(planetSystemName,planetName);
        ctx.json(planet);
    }

    /*public void sortPlanetsByName(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String sorting = ctx.queryParam("sort_by=name");


    }*/
}
