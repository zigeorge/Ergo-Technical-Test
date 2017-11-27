package igeorge.xtreme.ergotechnicaltest.model;

/**
 * Created by iGeorge on 27/11/17.
 */

public class MapUrl {
    private String base;
    private String origin;
    private String destination;
    private boolean alternatives;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isAlternatives() {
        return alternatives;
    }

    public void setAlternatives(boolean alternatives) {
        this.alternatives = alternatives;
    }
}
