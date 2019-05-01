package model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private LocalDate start;
    private LocalDate end;
    private String positionTitle;
    private String positionInformation;

    public Position(LocalDate start, LocalDate end, String positionTitle, String positionInformation) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        Objects.requireNonNull(positionTitle, "positionTitle must not be null");
        Objects.requireNonNull(positionInformation, "positionInformation must not be null");
        this.start = start;
        this.end = end;
        this.positionTitle = positionTitle;
        this.positionInformation = positionInformation;
    }

    public Position(LocalDate start, LocalDate end, String positionTitle) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        Objects.requireNonNull(positionTitle, "positionTitle must not be null");
        this.start = start;
        this.end = end;
        this.positionTitle = positionTitle;
    }

    public Position(LocalDate start, String positionTitle, String positionInformation) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(positionTitle, "positionTitle must not be null");
        Objects.requireNonNull(positionInformation, "positionInformation must not be null");
        this.start = start;
        this.positionTitle = positionTitle;
        this.positionInformation = positionInformation;
    }

    public Position(LocalDate start, String positionTitle) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(positionTitle, "positionTitle must not be null");
        this.start = start;
        this.positionTitle = positionTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return start.equals(position.start) &&
                end.equals(position.end) &&
                positionTitle.equals(position.positionTitle) &&
                positionInformation.equals(position.positionInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, positionTitle, positionInformation);
    }

    @Override
    public String toString() {
        return start + " - " + end +
                " " + positionTitle +
                "\n" + positionInformation;
    }
}

