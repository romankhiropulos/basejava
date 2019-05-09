package basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final String textSection;

    public TextSection(String textSection) {
        Objects.requireNonNull(textSection, "textSection must not be null");
        this.textSection = textSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return textSection.equals(that.textSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textSection);
    }

    @Override
    public String toString() {
        return textSection;
    }
}
