package basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProgressSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> progress;

    public ProgressSection() {
    }

    public ProgressSection(String... progress) {
        this(Arrays.asList(progress));
    }

    public ProgressSection(List<String> progress) {
        Objects.requireNonNull(progress, "progress must not be null");
        this.progress = progress;
    }

    public List<String> getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressSection that = (ProgressSection) o;
        return progress.equals(that.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(progress);
    }

    @Override
    public String toString() {
        return String.valueOf(progress);
    }
}
