package model;

import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String location;
    private final String locationLink;

    public Link(String location, String locationLink) {
        Objects.requireNonNull(location, "progress must not be null");
        this.location = location;
        this.locationLink = locationLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return location.equals(link.location) &&
                Objects.equals(locationLink, link.locationLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, locationLink);
    }

    @Override
    public String toString() {
        return location + "\n"
                + locationLink + "\n";
    }
}
