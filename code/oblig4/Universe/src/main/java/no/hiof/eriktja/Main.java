package no.hiof.eriktja;

import java.util.ArrayList;
import java.util.Collections;
import no.hiof.eriktja.repository.UniverseDataRepository;
import no.hiof.eriktja.model.*;
import no.hiof.eriktja.repository.UniverseRepository;

public class Main {
    public static void main(String[] args) {

        UniverseDataRepository milkyWay = new UniverseDataRepository();
        System.out.println("\n***************Oppgave 2.1 - Sammenligning og Sortering*******************");
        ArrayList<Planet> solSystem = milkyWay.getSpecificPlanetSystem("Solar System").getPlanets();
        System.out.println("\n***************Solar system not sorted*******************");
        for (Planet entity : solSystem)
            System.out.println(entity);

        System.out.println("\n***************Solar system sorted by Radius*******************");
        Collections.sort(solSystem);
        for (Planet entity : solSystem)
            System.out.println(entity);
    }
}
