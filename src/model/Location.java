package model;

import java.util.List;
import java.util.Objects;

public class Location {
    private final Link link;
    private final List<Position> position;

    public Location(String location, String link, List<Position> position) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.link = new Link(location, link);
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return link.equals(location.link) &&
                position.equals(location.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, position);
    }

    @Override
    public String toString() {
        return link + "\n"
                + position;
    }
}
