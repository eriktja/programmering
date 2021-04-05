package no.hiof.eriktja.controller;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.repository.UniverseRepository;
import io.javalin.http.Context;

import java.util.*;

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
        assert sort_by != null;
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
                    if(sort_by.equals("mass")){
                        if (aPlanet.getMassInKg() > otherPlanet.getMassInKg())
                            return 1;
                        else if (aPlanet.getMassInKg() < otherPlanet.getMassInKg())
                            return -1;
                    }
                    return 0;
                }
            });
        }
        ctx.json(planets);
    }

    public void getSpecificPlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        ctx.json(universeRepository.getSpecificPlanet(planetSystemName,planetName));
    }

    // Oppgave 2.3 - Delete
    public void deletePlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        ctx.json(universeRepository.deletePlanet(planetName, planetSystemName));
    }


    // Oppgave 2.5c - Felles kode mellom create og update
    public HashMap<String, String> planetInfo(Context ctx) {
        HashMap<String, String> planetInfoHashmap = new HashMap<>();
        planetInfoHashmap.put("name", ctx.formParam("name"));
        planetInfoHashmap.put("radius", ctx.formParam("radius"));
        planetInfoHashmap.put("mass", ctx.formParam("mass"));
        planetInfoHashmap.put("semiMajorAxis", ctx.formParam("semiMajorAxis"));
        planetInfoHashmap.put("eccentricity", ctx.formParam("eccentricity"));
        planetInfoHashmap.put("orbitalPeriod", ctx.formParam("orbitalPeriod"));
        planetInfoHashmap.put("pictureUrl", ctx.formParam("pictureUrl"));
        return planetInfoHashmap;
    }
    // Oppgave 2.4 - Create
    public void createPlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        // Create a new planet and set the values.
        Planet aPlanet = new Planet();
        aPlanet.setName(planetInfo(ctx).get("name"));
        aPlanet.setRadius(Double.parseDouble(planetInfo(ctx).get("radius")));
        aPlanet.setMass(Double.parseDouble(planetInfo(ctx).get("mass")));
        aPlanet.setSemiMajorAxis(Double.parseDouble(planetInfo(ctx).get("semiMajorAxis")));
        aPlanet.setEccentricity(Double.parseDouble(planetInfo(ctx).get("eccentricity")));
        aPlanet.setOrbitalPeriod(Double.parseDouble(planetInfo(ctx).get("orbitalPeriod")));
        aPlanet.setPictureUrl(planetInfo(ctx).get("pictureUrl"));

        ctx.json(universeRepository.createPlanet(aPlanet, planetSystemName));
    }
    // Oppgave 2.5 - Update
    /*public void updatePlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        String name = planetInfo(ctx).get("name");
        double radius = Double.parseDouble(planetInfo(ctx).get("radius"));
        double mass = Double.parseDouble(planetInfo(ctx).get("mass"));
        double semiMajorAxis = Double.parseDouble(planetInfo(ctx).get("semiMajorAxis"));
        double eccentricity = Double.parseDouble(planetInfo(ctx).get("eccentricity"));
        double orbitalPeriod = Double.parseDouble(planetInfo(ctx).get("orbitalPeriod"));
        String pictureUrl = planetInfo(ctx).get("pictureUrl");
        ctx.json(universeRepository.updatePlanet(
                planetName,
                planetSystemName,
                name,
                radius,
                mass,
                semiMajorAxis,
                eccentricity,
                orbitalPeriod,
                pictureUrl));
    }*/
    // Oppgave 2.5 - Update
    public void updatePlanet(Context ctx) {
        String planetSystemName = ctx.pathParam(":planet-system-id");
        String planetName = ctx.pathParam(":planet-id");
        ctx.json(universeRepository.updatePlanet(
                planetName,             // Planetname so the method updates the correct object
                planetSystemName,       // Systemname so we can find the correct planet
                planetInfo(ctx)));      // A Hashmap containing the changes made
    }
}