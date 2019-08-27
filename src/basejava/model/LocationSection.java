package basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Location> locations;

    public LocationSection() {
    }

    public LocationSection(Location... locations) {
        this(Arrays.asList(locations));
    }

    public LocationSection(List<Location> locations) {
        Objects.requireNonNull(locations, "location must not be null");
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationSection that = (LocationSection) o;
        return locations.equals(that.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locations);
    }

    @Override
    public String toString() {
        return String.valueOf(locations);
    }
}
