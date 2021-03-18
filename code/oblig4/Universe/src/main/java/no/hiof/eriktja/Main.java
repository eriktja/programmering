package no.hiof.eriktja;

import java.util.ArrayList;
import java.util.Collections;
import no.hiof.eriktja.repository.UniverseDataRepository;
import no.hiof.eriktja.model.*;

public class Main {
    public static void main(String[] args) {

        UniverseDataRepository milkyWay = new UniverseDataRepository();
        System.out.println("\n***************Oppgave 2.1 - Sammenligning og Sortering*******************");
        System.out.println("\n***************Celestial Body sort*******************");
        ArrayList<Planet> solSystem = milkyWay.getSpecificPlanetSystem("Solar System").getPlanets();
        System.out.println("\n***************Solar system not sorted*******************");
        for (Planet entity : solSystem)
            System.out.println(entity);
        // Print all planets in a system sorted by radius using compareTo
        System.out.println("\n***************Solar system sorted by Radius*******************");
        Collections.sort(solSystem);
        for (Planet entity : solSystem)
            System.out.println(entity);


        System.out.println("\n***************Planet system sort*******************");
        System.out.println("\n***************Planet systems not sorted*******************");
        ArrayList<PlanetSystem> planetSystems = milkyWay.getPlanetSystems();
        for (PlanetSystem planetSystem : planetSystems)
            System.out.println(planetSystem);
        // Print all planetSystems sorted by amount of planets in system using compareTo
        System.out.println("\n***************Planet systems sorted by size*******************");
        Collections.sort(planetSystems);
        for (PlanetSystem planetSystem : planetSystems)
            System.out.println(planetSystem);
    }
}
