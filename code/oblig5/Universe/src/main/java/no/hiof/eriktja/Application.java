package no.hiof.eriktja;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.eriktja.controller.PlanetController;
import no.hiof.eriktja.controller.PlanetSystemController;
import no.hiof.eriktja.repository.UniverseCSVRepository;
import no.hiof.eriktja.repository.UniverseDataRepository;
import no.hiof.eriktja.repository.UniverseJSONRepository;
import no.hiof.eriktja.repository.UniverseRepository;
import org.jetbrains.annotations.NotNull;

public class Application {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start();
        // "/" = root folder. Handler = interface with 1 method

        app.config.enableWebjars();

        // Instances of the following classes
        //UniverseRepository universeRepository = new UniverseJSONRepository("planets_100.json");
        UniverseRepository universeRepository = new UniverseJSONRepository("testJSONfile.json");
        PlanetSystemController planetSystemController = new PlanetSystemController(universeRepository);
        PlanetController planetController = new PlanetController(universeRepository);

        app.get("/", ctx -> ctx.redirect("/planet-system"));
        //app.get("/api/planet-system/:planet-system-id/planets/:planet-id/delete",
        //        ctx -> ctx.redirect("/planet-system/:planet-system-id/planets"));
        /*app.get("/api/planet-system/:planet-system-id/planets/:planet-id/update",
                ctx -> ctx.redirect("/planet-system/:planet-system-id/planets"));
        app.get("/api/planet-system/:planet-system-id/planets/create",
                ctx -> ctx.redirect("/planet-system/:planet-system-id/planets"));*/

        app.get("/planet-system/", new VueComponent("planet-system-overview"));
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id/planets/create", new VueComponent("planet-create"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id/update", new VueComponent("planet-update"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));

        // Testing write to file for both JSON and CSV
        //UniverseCSVRepository.writeToFile(universeRepository.getPlanetSystems(), "testCSVfile.csv");
        //UniverseJSONRepository.writeToFile(universeRepository.getPlanetSystems(), "testJSONfile.json");


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
        app.post("/api/planet-system/:planet-system-id/planets/create", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.createPlanet(ctx);
            }
        });
        app.post("/api/planet-system/:planet-system-id/planets/:planet-id/update", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.updatePlanet(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.getSpecificPlanet(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id/delete", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetController.deletePlanet(ctx);
            }
        });
    }
}
