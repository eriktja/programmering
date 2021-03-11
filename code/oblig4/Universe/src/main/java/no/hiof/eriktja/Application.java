package no.hiof.eriktja;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.eriktja.controller.PlanetController;
import no.hiof.eriktja.controller.PlanetSystemController;
import no.hiof.eriktja.model.PlanetSystem;
import no.hiof.eriktja.repository.UniverseDataRepository;
import no.hiof.eriktja.repository.UniverseRepository;
import org.jetbrains.annotations.NotNull;

public class Application {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start();
        // "/" = root folder. Handler = interface with 1 method

        app.config.enableWebjars();

        app.get("/helloworld", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                ctx.result("Hello World");
            }
        });

        UniverseRepository universeRepository = new UniverseDataRepository();
        PlanetSystemController planetSystemController = new PlanetSystemController(universeRepository);
        PlanetController planetController = new PlanetController(universeRepository);

        app.get("/planet-system/", new VueComponent("planet-system-overview"));
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        /*app.get("/planet-system/:planet-system-id?sort_by=mass", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id?sort_by=radius", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id?sort_by=name", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id?sort_by=num", new VueComponent("planet-system-detail"));*/
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));

        UniverseDataRepository milkyWay = new UniverseDataRepository();

        app.get("/api/planet-system/", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getAllPlanetSystems(ctx);
            }
        });
        app.get("api/planet-system/:planet-system-id", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getSpecificPlanetSystem(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id/planets", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.getAllPlanetsInSystem(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.getSpecificPlanet(ctx);
            }
        });/*
        app.get("/api/planet-system/:planet-system-id?sort_by=mass", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.sortPlanetByMass(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id?sort_by=radius", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.sortPlanetByRadius(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id?sort_by=name", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.sortPlanetByName(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id?sort_by=num", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.sortPlanetByOrder(ctx);
            }
        });*/
    }
}
