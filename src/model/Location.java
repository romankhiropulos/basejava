package model;

import java.time.LocalDate;
import java.util.Objects;

public class Location {
    private final Link locationLink;
    private final LocalDate start;
    private final LocalDate end;
    private final String position;
    private final String information;

    public Location(Link locationLink, LocalDate start, LocalDate end, String position, String information) {
        Objects.requireNonNull(locationLink, "locationLink must not be null");
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(information, "information must not be null");
        this.locationLink = locationLink;
        this.start = start;
        this.end = end;
        this.position = position;
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLink.equals(location.locationLink) &&
                start.equals(location.start) &&
                end.equals(location.end) &&
                position.equals(location.position) &&
                information.equals(location.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationLink, start, end, position, information);
    }

    @Override
    public String toString() {
        return locationLink + "\n"
                + start + "\n"
                + end + "\n"
                + position + "\n"
                + information;
    }
}
