package basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String locationName;
    private String locationLink;

    public Link() {
    }

    public Link(String location, String locationLink) {
        Objects.requireNonNull(location, "progress must not be null");
        this.locationName = location;
        this.locationLink = locationLink != null ? locationLink : "";
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationLink() {
        return locationLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return locationName.equals(link.locationName) &&
                Objects.equals(locationLink, link.locationLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLink);
    }

    @Override
    public String toString() {
        return locationName + "\n"
                + locationLink + "\n";
    }
}
