package model;

import java.util.List;
import java.util.Objects;

public class Location {
    private final Link locationLink;
    private final List<Position> position;

    public Location(String location, String locationLink, List<Position> position) {
        this.locationLink = new Link(location, locationLink);
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLink.equals(location.locationLink) &&
                position.equals(location.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationLink, position);
    }

    @Override
    public String toString() {
        return locationLink + "\n"
                + position;
    }
}
