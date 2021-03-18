package no.hiof.eriktja.controller;

import io.javalin.http.Context;
import no.hiof.eriktja.repository.UniverseRepository;


public class PlanetSystemController{
    private UniverseRepository universeRepository;

    public PlanetSystemController(UniverseRepository universeRepository) {
        this.universeRepository = universeRepository;
    }

    public void getAllPlanetSystems(Context ctx) {
        ctx.json(universeRepository.getPlanetSystems());
    }
    public void getSpecificPlanetSystem(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        ctx.json(universeRepository.getSpecificPlanetSystem(planetSystemName));
    }

}
