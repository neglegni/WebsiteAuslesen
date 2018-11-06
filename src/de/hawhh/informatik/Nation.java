package de.hawhh.informatik;

import java.util.Objects;

public class Nation {

    private final String name;
    private final String capital;
    private final long area;
    private final long numInhabitants;
    private final String flagRef;

    public Nation(String name, String capital, long area, long numInhabitants, String flagRef){
        this.name = name;
        this.capital = capital;
        this.area = area;
        this.numInhabitants = numInhabitants;
        this.flagRef = flagRef;
    }

    public String toString(){
        return String.format("%s:Capital-%s:Area-%d:Inhabitants-%d:FlagRef-%s",name,capital,area,numInhabitants, flagRef);
    }

    public String toShortString(NationAttribute attribute) {
        switch (attribute) {
            case NAME: return name;
            case CAPITAL: return capital;
            case AREA: return Objects.toString(area);
            case INHABITANTS: return Objects.toString(numInhabitants);
            case FLAG: return flagRef;
            default: return "";
        }
    }
}
