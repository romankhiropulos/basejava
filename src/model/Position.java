package model;

import util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

public class Position {
    private LocalDate start;
    private LocalDate end;
    private String title;
    private String information;

    public Position(LocalDate start, LocalDate end, String title, String information) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.start = start;
        this.end = end;
        this.title = title;
        this.information = information;
    }

    public Position(int yearStart, Month monthStart, int yearEnd, Month monthEnd, String title, String information) {
        this(DateUtil.of(yearStart, monthStart), DateUtil.of(yearEnd, monthEnd), title, information);
        this.title = title;
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return start.equals(position.start) &&
                end.equals(position.end) &&
                title.equals(position.title) &&
                Objects.equals(information, position.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, title, information);
    }

    @Override
    public String toString() {
        return start + " - " + end +
                " " + title +
                "\n" + information;
    }
}

