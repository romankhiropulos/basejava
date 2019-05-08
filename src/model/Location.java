package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static util.DateUtil.NOW;
import static util.DateUtil.of;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link link;
    private final List<Position> positions;

    public Location(String name, String link, Position... positions) {
        this(new Link(name, link), Arrays.asList(positions));
    }

    public Location(Link link, List<Position> positions) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(positions, "positions must not be null");
        this.link = link;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return link.equals(location.link) &&
                positions.equals(location.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        return link + "\n"
                + positions;
    }

    public static class Position implements Serializable {
        private final LocalDate start;
        private final LocalDate end;
        private final String title;
        private final String description;

        public Position(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Position(LocalDate start, LocalDate end, String title, String description) {
            Objects.requireNonNull(start, "start must not be null");
            Objects.requireNonNull(end, "end must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.start = start;
            this.end = end;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return start.equals(position.start) &&
                    end.equals(position.end) &&
                    title.equals(position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, title, description);
        }

        @Override
        public String toString() {
            return start + " - " + end +
                    " " + title +
                    "\n" + description;
        }
    }
}
