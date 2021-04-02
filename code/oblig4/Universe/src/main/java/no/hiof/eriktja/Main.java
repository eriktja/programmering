package no.hiof.eriktja;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
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


        //skrivTilFil(milkyWay.getPlanetSystems(), "planetSystems.json");
        ArrayList<PlanetSystem> returnSystem = lesFraFil("planetSystems.json");
        System.out.println(returnSystem);

    }
    private static void skrivTilFil(ArrayList<PlanetSystem> planetSystems, String filnavn){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filnavn), planetSystems);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static ArrayList<PlanetSystem> lesFraFil(String filnavn){
        ArrayList<PlanetSystem> planetSystemReturnList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PlanetSystem[] planetSystemArray = objectMapper.readValue(new File(filnavn), PlanetSystem[].class);
            planetSystemReturnList.addAll(Arrays.asList(planetSystemArray));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return planetSystemReturnList;
    }
}
