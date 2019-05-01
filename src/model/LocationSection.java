package model;

import java.util.List;
import java.util.Objects;

public class LocationSection extends AbstractSection {
    private final List<Location> location;

    public LocationSection(List<Location> location) {
        Objects.requireNonNull(location, "location must not be null");
        this.location = location;
    }

    public List<Location> getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationSection that = (LocationSection) o;
        return location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return String.valueOf(location);
    }
}
