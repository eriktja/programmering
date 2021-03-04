package no.hiof.eriktja.model;

import org.jetbrains.annotations.NotNull;

// Star.java is a subclass of Planet.java.
public class Star extends CelestialBody implements Comparable<CelestialBody>{
    private int effectiveTemp;

    private static final double SUN_MASS_IN_KG = 1.98892E30;
    private static final double SUN_RADIUS_IN_KG = 695700;

    public Star(String name, double radius, double mass, int effectiveTemp) {
        super(name, radius, mass);
        this.effectiveTemp = effectiveTemp;
    }

    public int getEffectiveTemp() {
        return effectiveTemp;
    }

    public void setEffectiveTemp(int effectiveTemp) {
        this.effectiveTemp = effectiveTemp;
    }

    // Override the KmRadius and KgMass methods from Planet
    @Override
    public double getKmRadius() {
        return getRadius() * SUN_RADIUS_IN_KG;
    }
    @Override
    public double getKgMass() {
        return getMass() * SUN_MASS_IN_KG;
    }

    @Override
    public String toString() {
        return "The " + getName() +
                " has an effective temperature of "
                + getEffectiveTemp() + "K";
    }


    @Override
    public int compareTo(@NotNull CelestialBody otherStar) {
        if (getRadius() == otherStar.getRadius())
                return 0;
        else if (getRadius() < otherStar.getRadius())
                return -1;
        return 1;
    }
}
