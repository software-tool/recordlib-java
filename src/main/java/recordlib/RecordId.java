package recordlib;

import recordlib.path.PathSteps;

import java.util.Objects;

public class RecordId {

    private String name = null;
    private Long id = null;
    private PathSteps steps = null;

    private String key = null;

    private RecordId parentItemId = null;

    // Getter

    public Long getId() {
        return id;
    }

    public boolean hasId() {
        return id != null;
    }

    public PathSteps getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public boolean hasNameId() {
        return id != null && name != null;
    }

    // Setter

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Parent

    public PathSteps getParentSteps() {
        if (parentItemId == null) {
            return null;
        }

        return parentItemId.getSteps();
    }

    public Long getParentId() {
        if (parentItemId == null) {
            return null;
        }

        return parentItemId.getId();
    }

    public String getParentName() {
        if (parentItemId == null) {
            return null;
        }

        return parentItemId.getName();
    }

    public RecordId getParentItemId() {
        return parentItemId;
    }

    public boolean hasParent() {
        if (parentItemId == null) {
            return false;
        }

        return parentItemId.hasNameId();
    }

    // Hashcode, Equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordId itemId = (RecordId) o;
        return Objects.equals(name, itemId.name) && Objects.equals(id, itemId.id) && Objects.equals(steps, itemId.steps) && Objects.equals(key, itemId.key) && Objects.equals(parentItemId, itemId.parentItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, steps, key, parentItemId);
    }
}
