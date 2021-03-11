package no.hiof.eriktja;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.eriktja.controller.PlanetController;
import no.hiof.eriktja.controller.PlanetSystemController;
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

        // Instances of the following classes
        UniverseRepository universeRepository = new UniverseDataRepository();
        PlanetSystemController planetSystemController = new PlanetSystemController(universeRepository);
        PlanetController planetController = new PlanetController(universeRepository);
        // Creating an instance of UniverseDataRepository creates 
        UniverseDataRepository milkyWay = new UniverseDataRepository();

        app.get("/planet-system/", new VueComponent("planet-system-overview"));
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));


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
        });
    }
}
