package raf.draft.dsw.model.nodes;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Building.class, name = "building"),
        @JsonSubTypes.Type(value = Room.class, name = "room"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class DraftNodeComposite extends DraftNode {


    private List<DraftNode> children = new ArrayList<>();

    public DraftNodeComposite() {
    }

    public DraftNodeComposite(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    public void addChild(DraftNode child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(DraftNode child) {
        children.remove(child);
        child.setParent(null);
    }

    public List<DraftNode> getChildren() {
        return children;
    }

    public void setChildren(List<DraftNode> children) {
        this.children = children;
    }
}
