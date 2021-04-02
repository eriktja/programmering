package no.hiof.eriktja.repository;

import no.hiof.eriktja.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UniverseCSVRepository implements UniverseRepository{
    private String fileName;
    private HashMap<String, PlanetSystem> planetSystemHashMap;


    public UniverseCSVRepository(String fileName) {
        this.fileName = fileName;
        readFromFile(this.fileName);
    }

    private void readFromFile(String fileName) {
        planetSystemHashMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String singleLine;
            Star star = new Star();
            PlanetSystem planetSystem = new PlanetSystem();
            Planet planet;
            while ((singleLine = bufferedReader.readLine()) != null){
                String[] stringArray = singleLine.split(";");
                if (!planetSystemHashMap.containsKey(stringArray[0])) {
                    star = new Star(
                            stringArray[2],                         // star name
                            Double.parseDouble(stringArray[3]),     // star radius
                            Double.parseDouble(stringArray[4]),     // star mass
                            Double.parseDouble(stringArray[5]),     // star temperature
                            stringArray[6]);                        // star picture url
                    planetSystem = new PlanetSystem(
                            stringArray[0],                         // system name
                            star,                                   // system star
                            stringArray[1]);                        // system picture url
                    planetSystemHashMap.put(planetSystem.getName(), planetSystem);
                }
                planet = new Planet(
                        stringArray[7],                             // planet name
                        Double.parseDouble(stringArray[8]),         // planet radius
                        Double.parseDouble(stringArray[9]),         // planet mass
                        Double.parseDouble(stringArray[10]),        // planet semi major axis
                        Double.parseDouble(stringArray[11]),        // planet eccentricity
                        Double.parseDouble(stringArray[12]),        // planet orbital period
                        star,                                       // planet central celestial body
                        stringArray[13]);                           // planet picture url
                planetSystem.addPlanet(planet);                     // add planet to planetSystem arraylist
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }


    public static void writeToFile(ArrayList<PlanetSystem> planetSystems, String fileName)  {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName)))){
            for (PlanetSystem planetSystem : planetSystems){
                for (Planet planet : planetSystem.getPlanets()) {
                    bufferedWriter.write(
                            planetSystem.getName() + ";" +
                                planetSystem.getPictureUrl() + ";" +
                                planetSystem.getCenterStar().getName() + ";" +
                                planetSystem.getCenterStar().getRadius() + ";" +
                                planetSystem.getCenterStar().getMass() + ";" +
                                planetSystem.getCenterStar().getEffectiveTemp() + ";" +
                                planetSystem.getCenterStar().getPictureUrl() + ";" +
                                planet.getName() + ";" +
                                planet.getRadius() + ";" +
                                planet.getMass() + ";" +
                                planet.getSemiMajorAxis() + ";" +
                                planet.getEccentricity() + ";" +
                                planet.getOrbitalPeriod() + ";" +
                                planet.getPictureUrl()
                    );
                    bufferedWriter.newLine();
                }
            }


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public ArrayList<PlanetSystem> getPlanetSystems() {
        return new ArrayList<>(this.planetSystemHashMap.values());
    }

    @Override
    public PlanetSystem getSpecificPlanetSystem(String name) {
        return this.planetSystemHashMap.get(name);
    }

    @Override
    public ArrayList<Planet> getAllPlanetsInSystem(String name) {
        return new ArrayList<Planet>(this.planetSystemHashMap.get(name).getPlanets());
    }

    @Override
    public Planet getSpecificPlanet(String planetSystemName, String planetName) {
        return getSpecificPlanetSystem(planetSystemName).findPlanet(planetName);
    }

    @Override
    public ArrayList<Planet> deletePlanet(String planetName, String planetSystemName) {
        return null;
    }

    @Override
    public Planet updatePlanet(Planet planet, String planetSystemName) {
        return null;
    }

    @Override
    public Planet createPlanet(Planet planet, String planetSystemName) {
        return null;
    }
}
