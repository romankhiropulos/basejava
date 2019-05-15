package basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String location;
    private String locationLink;

    public Link() {
    }

    public Link(String location, String locationLink) {
        Objects.requireNonNull(location, "progress must not be null");
        this.location = location;
        this.locationLink = locationLink != null ? locationLink : "";
    }

    public String getLocation() {
        return location;
    }

    public String getLocationLink() {
        return locationLink;
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
