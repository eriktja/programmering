package no.hiof.eriktja.repository;

import no.hiof.eriktja.model.Planet;
import no.hiof.eriktja.model.PlanetSystem;
import no.hiof.eriktja.model.Star;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UniverseDataRepository implements UniverseRepository {
    private ArrayList<PlanetSystem> planetSystems = new ArrayList<>();

    public UniverseDataRepository() {
        // A star which will become the central star of solarSystem
        Star sol = new Star("Sun", 1.0, 1.0, 5777, "http://bit.ly/333CTus");
        // A star which will become the central star of Kepler-11 System
        Star keplerStar = new Star ("Kepler-11",1.889E30,710310,5680, "http://bit.ly/336nzNZ");
        // A new PlanetSystem called solarSystem
        PlanetSystem solarSystem = new PlanetSystem("Solar System", sol, "http://bit.ly/3cVhuZc");
        // A new PlanetSystem called keplerSystem
        PlanetSystem kepler11System = new PlanetSystem("Kepler-11 System", keplerStar, "http://bit.ly/2Iz4jPB");
        // Creating all the Planet-objects.
        Planet mercury = new Planet(
                "Mercury",
                0.03412549655905556,
                1.7297154899894627E-4,
                0.387,
                0.206,
                88.0,
                sol,
                "http://bit.ly/2TB2Heo");
        Planet venus = new Planet("Venus",
                0.08465003077267387,
                0.002564278187565859,
                0.723,
                0.007,
                225.0,
                sol,
                "http://bit.ly/2W3p4L9");
        Planet earth = new Planet("Earth",
                0.08911486599899289,
                0.003146469968387777,
                1.0,
                0.017,
                365.0,
                sol,
                "http://bit.ly/33bvXLZ");
        Planet mars = new Planet("Mars",
                0.04741089912158004,
                3.3667017913593256E-4,
                1.524,
                0.093,
                687.0,
                sol,
                "http://bit.ly/3aGyFvr");
        Planet jupiter = new Planet("Jupiter",
                1.0,
                1.0,
                5.20440,
                0.049,
                4380.0,
                sol,
                "http://bit.ly/2Q0fjK3");
        Planet saturn = new Planet("Saturn",
                0.8145247020645666,
                0.2994204425711275,
                9.5826,
                0.057,
                10585.0,
                sol,
                "http://bit.ly/2W0sqic");
        Planet uranus = new Planet("Uranus",
                0.35475297935433336,
                0.04573761854583773,
                19.2184,
                0.046,
                30660.0,
                sol,
                "http://bit.ly/335pbHy");
        Planet neptune = new Planet("Neptune",
                0.34440217087226543,
                0.05395152792413066,
                30.11,
                0.010,
                60225.0,
                sol,
                "http://bit.ly/38AyEba");


        Planet keplerB = new Planet("Kepler-11b",
                0.01352982086406744,
                0.17554411682426005,
                1.36134E7,
                0.045,10.0,
                keplerStar,
                "http://bit.ly/335pbHy");
        Planet keplerC = new Planet("Kepler-11c",
                0.04247734457323498,
                0.28070273597045825,
                1.5857E7,
                0.026,
                13.0,
                keplerStar,
                "http://bit.ly/335pbHy");
        Planet keplerD = new Planet("Kepler-11d",
                0.019193466807165438,
                0.3056565769596598,
                2.3786E7,
                0.004,
                22.0,
                keplerStar,
                "http://bit.ly/335pbHy");
        Planet keplerE = new Planet("Kepler-11e",
                0.026430347734457325,
                0.4027863257427404,
                2.9021E7,
                0.012,
                31.0,
                keplerStar,
                "http://bit.ly/335pbHy");
        Planet keplerF = new Planet("Kepler-11f",
                0.007236880927291886,
                0.2325854641078722,
                3.7399E7,
                0.013,
                36.0,
                keplerStar,
                "http://bit.ly/335pbHy");
        Planet keplerG = new Planet("Kepler-11g",
                0.9439409905163331,
                0.32614838023834836,
                6.9114E7,
                0.015,
                118.0,
                keplerStar,
                "http://bit.ly/335pbHy");

        // Adding all the Planet-objects to the solarSystem ArrayList "planets"
        solarSystem.addPlanet(mercury);
        solarSystem.addPlanet(venus);
        solarSystem.addPlanet(earth);
        solarSystem.addPlanet(mars);
        solarSystem.addPlanet(jupiter);
        solarSystem.addPlanet(saturn);
        solarSystem.addPlanet(uranus);
        solarSystem.addPlanet(neptune);

        kepler11System.addPlanet(keplerB);
        kepler11System.addPlanet(keplerC);
        kepler11System.addPlanet(keplerD);
        kepler11System.addPlanet(keplerE);
        kepler11System.addPlanet(keplerF);
        kepler11System.addPlanet(keplerG);


        planetSystems.add(solarSystem);
        planetSystems.add(kepler11System);
    }

    @Override
    public ArrayList<PlanetSystem> getPlanetSystems() {
        return new ArrayList<PlanetSystem>(planetSystems);
    }

    @Override
    public PlanetSystem getSpecificPlanetSystem(String name) {
        for (PlanetSystem aSystem : getPlanetSystems()) {
            if (aSystem.getName().equalsIgnoreCase(name))
                return aSystem;
        }
        return null;
    }

    @Override
    public ArrayList<Planet> getAllPlanetsInSystem(String name) {
        return getSpecificPlanetSystem(name).getPlanets();
    }

    @Override
    public Planet getSpecificPlanet(String planetSystemName, String planetName) {
        PlanetSystem planetSystem = getSpecificPlanetSystem(planetSystemName);
        for (Planet planet : planetSystem.getPlanets()){
            if (planet.getName().equalsIgnoreCase(planetName))
                return planet;
        }
        return null;
    }



    @Override
    public ArrayList<Planet> sortPlanetByName(String systemName) {
        ArrayList<Planet> planetsInSystem = getSpecificPlanetSystem(systemName).getPlanets();
        Collections.sort(planetsInSystem, new Comparator<Planet>() {
            @Override
            public int compare(Planet aPlanet, Planet otherPlanet) {
                return aPlanet.getName().compareTo(otherPlanet.getName());
            }
        });
        return planetsInSystem;
    };


    @Override
    public ArrayList<Planet> sortPlanetByMass(String systemName) {
        ArrayList<Planet> planetsInSystem = getSpecificPlanetSystem(systemName).getPlanets();
        Collections.sort(planetsInSystem, new Comparator<Planet>() {
            @Override
            public int compare(Planet aPlanet, Planet otherPlanet) {
                return (int)(aPlanet.getKgMass() - otherPlanet.getKgMass());
            }
        });
        return planetsInSystem;
    }

    @Override
    public ArrayList<Planet> sortPlanetByOrder(String systemName) {
        return getSpecificPlanetSystem(systemName).getPlanets();
    }

    @Override
    public ArrayList<Planet> sortPlanetByRadius(String systemName) {
        ArrayList<Planet> planetsInSystem = getSpecificPlanetSystem(systemName).getPlanets();
        Collections.sort(planetsInSystem);
        return planetsInSystem;
    }
}
