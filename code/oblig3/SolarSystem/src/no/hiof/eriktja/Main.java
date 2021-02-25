package no.hiof.eriktja;

public class Main {

    public static void main(String[] args) {
        // A star which will become the central star of solarSystem
        Star sol = new Star("Sun", 1.0, 1.0, 5777);
        // A new PlanetSystem called solarSystem
        PlanetSystem solarSystem = new PlanetSystem("Solar System", sol);

        // Creating all the Planet-objects.
        Planet mercury = new Planet(
                "Mercury",
                0.03412549655905556,
                1.7297154899894627E-4,
                0.387,
                0.206,
                88.0,
                sol);
        Planet venus = new Planet("Venus",
                0.08465003077267387,
                0.002564278187565859,
                0.723,
                0.007,
                225.0,
                sol);
        Planet earth = new Planet("Earth",
                0.08911486599899289,
                0.003146469968387777,
                1.0,
                0.017,
                365.0,
                sol);
        Planet mars = new Planet("Mars",
                0.04741089912158004,
                3.3667017913593256E-4,
                1.524,
                0.093,
                687.0,
                sol);
        Planet jupiter = new Planet("Jupiter",
                1.0,
                1.0,
                5.20440,
                0.049,
                4380.0,
                sol);
        Planet saturn = new Planet("Saturn",
                0.8145247020645666,
                0.2994204425711275,
                9.5826,
                0.057,
                10585.0,
                sol);
        Planet uranus = new Planet("Uranus",
                0.35475297935433336,
                0.04573761854583773,
                19.2184,
                0.046,
                30660.0,
                sol);
        Planet neptune = new Planet("Neptune",
                0.34440217087226543,
                0.05395152792413066,
                30.11,
                0.010,
                60225.0,
                sol);

        // Adding all the Planet-objects to the solarSystem ArrayList "planets"
        solarSystem.setPlanets(mercury);
        solarSystem.setPlanets(venus);
        solarSystem.setPlanets(earth);
        solarSystem.setPlanets(mars);
        solarSystem.setPlanets(jupiter);
        solarSystem.setPlanets(saturn);
        solarSystem.setPlanets(uranus);
        solarSystem.setPlanets(neptune);


        System.out.println("*******Opppgave 2.2 - Navn*********");
        System.out.println("Hent planet i solsystem: \n" + solarSystem.findPlanet("Jupiter"));

        System.out.println("\n*******Opppgave 2.3 - Konstanter*********");
        System.out.printf("The mass of the %s in kilograms is %s\n", sol.getName(), sol.getKgMass());
        System.out.printf("The mass of the %s in kilograms is %s\n", earth.getName(), earth.getKgMass());

        System.out.println("\n***************Oppgave 2.5 - Avstand*******************");
        for (int i = 0; i <= 360; i += 90)
            System.out.printf("The %s distance to the sun at %d degrees is %.2f\n", earth.getName(), i, earth.getDistanceToCentralBodyKm(i));

        System.out.println("\n***************Oppgave 2.6 - Hastighet*******************");
        for (int i = 0; i <=180; i += 45)
            System.out.printf("The velocity of the %s is %.2fkm/s\n", earth.getName(), earth.getOrbitalVelocityKm(i));


        System.out.println("\n**************BONUS******************");
        System.out.println("**************Oppgave3.2 - Jordavstand******************");
        System.out.printf("%ss largest distance from the sun is: %.2f\n",
                earth.getName(), earth.getLargestDistanceToCentralBody());
        System.out.printf("%ss shortest distance from the sun is: %.2f\n",
                earth.getName(), earth.getShortestDistanceToCentralBody());

        System.out.println("\n***************Oppgave 3.3 - Trigonometri***************");
        System.out.printf("The distance bewteen Mars and Saturn after 180 days is: %.2f\n",
                mars.getPlanetRelativeDistance(saturn, 180));
        System.out.println("\n*****Test på at koden fungerer******");
        System.out.printf("The distance bewteen the earth and saturn after 180 days is: %.2f\n",
                earth.getPlanetRelativeDistance(saturn, 180));
    }
}


